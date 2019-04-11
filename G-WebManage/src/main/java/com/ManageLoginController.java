package com;

import auxiliary.loginObject.GuaranteeAuthorization;
import auxiliary.subassembly.tree.ztree.impl.ResourcesZTreeImpl;
import com.alibaba.fastjson.JSON;
import com.mapper.ManageLoginMapper;
import com.service.manageLogin.IManageLogin;
import com.sun.org.apache.regexp.internal.RE;
import communal.Result;
import communal.util.param.ParameterUtil;
import model.dto.manage.ManageLoginDTO;
import model.entity.Resources;
import model.vo.ZTreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sys.GlobalConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ManageLoginController {

    @Autowired
    private IManageLogin manageLogin;

    @Autowired
    @Qualifier("resourcesZTreeImpl")
    private ResourcesZTreeImpl resourcesZTreeImpl;

    @Autowired
    private ManageLoginMapper manageLoginMapper;

    /**
     * 跳转到登陆页面
     * @return
     */
    @GetMapping(value = "/login")
    public String login() {
        return "/login";
    }

    /**
     * 登陆
     * @param account
     * @param pws
     * @param request
     * @return
     */
    @PostMapping(value = "/manageLogin")
    @ResponseBody
    public String manageLogin(String account, String pws, HttpServletRequest request) {

        ManageLoginDTO manageLoginDTO = new ManageLoginDTO();
        manageLoginDTO.setAccount(ParameterUtil.stringCheck(account, null));
        manageLoginDTO.setPws(ParameterUtil.stringCheck(pws, null));
        Result result = manageLoginDTO.checkDTO();
        if (!result.isSuccess()) {return JSON.toJSONString(result);}

        result = manageLogin.login(manageLoginDTO);
        if (result.isSuccess()) {
            HttpSession session = request.getSession();
            session.setAttribute(GlobalConstants.Manage_SessionObject, result.getData());
        }
        return JSON.toJSONString(result);
    }

    /**
     * 退出登陆
     * @param request
     * @return
     */
    @GetMapping(value = "/logOut")
    public String logOut(HttpServletRequest request) {

        request.getSession().removeAttribute(GlobalConstants.Manage_SessionObject);
        request.getSession().invalidate();
        return "login";
    }

    /**
     * 返回登陆用户菜单
     * @param request
     * @return
     */
    @GetMapping(value = "/manageMenu")
    @ResponseBody
    public String manageMenu(HttpServletRequest request) {

        HttpSession session = request.getSession();
        GuaranteeAuthorization guaranteeAuthorization =
                (GuaranteeAuthorization)session.getAttribute(GlobalConstants.Manage_SessionObject);
        if (null != guaranteeAuthorization) {
            return guaranteeAuthorization.getMenuList();
        } else {
            return null;
        }
    }

    /**
     * 返回登陆用户菜单
     * @param request
     * @return
     */
    @GetMapping(value = "/manageMenu2")
    @ResponseBody
    public String manageMenu2(HttpServletRequest request) {

        List<Resources> resourcesList = manageLoginMapper.getResourcesForEmployeeID(1, 1l);
        List<ZTreeNode> zTreeNodeList = resourcesZTreeImpl.getMenuString(resourcesList);
        return JSON.toJSONString(zTreeNodeList);
    }
}
