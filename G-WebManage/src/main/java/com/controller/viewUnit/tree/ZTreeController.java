package com.controller.viewUnit.tree;

import auxiliary.subassembly.tree.ztree.IZTree;
import com.alibaba.fastjson.JSON;
import communal.util.param.ParameterUtil;
import model.vo.ZTreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value = "ztree")
public class ZTreeController {

    @Autowired
    @Qualifier("officeZTreeImpl")
    private IZTree officeZTreeImpl;

    @Autowired
    @Qualifier("resourcesSelectProxyImpl")
    private IZTree resourcesSelectProxyImpl;

    @Autowired
    @Qualifier("productCategoryZTreeImpl")
    private IZTree productCategoryZTreeImpl;

    /**
     * 机构树形结构
     * @param request
     * @return
     */
    @GetMapping(value="getOfficeZTree")
    @ResponseBody
    public List<ZTreeNode> getOfficeZTree(HttpServletRequest request) {
        List<ZTreeNode> zTreeNodeList = officeZTreeImpl.getTree(request.getParameter("id"));
        return zTreeNodeList;
    }

    /**
     * 资源树形结构
     * @param request
     * @return
     */
    @GetMapping(value="getResourcesZTree")
    @ResponseBody
    public String getResourcesZTree(HttpServletRequest request) {
        String id = request.getParameter("id");
        String roleID = ParameterUtil.stringCheck(request.getParameter("roleID"), null);
        String type = ParameterUtil.stringCheck(request.getParameter("type"), "ALL");
        List<ZTreeNode> zTreeNodeList = resourcesSelectProxyImpl.getTree(id, type, roleID);
        return JSON.toJSONString(zTreeNodeList);
    }

    /**
     * 商品分类树形结构
     * @param request
     * @return
     */
    @GetMapping(value="getProductCategoryZTree")
    @ResponseBody
    public String getProductCategoryZTree(HttpServletRequest request) {
        String id = request.getParameter("id");
        List<ZTreeNode> zTreeNodeList = productCategoryZTreeImpl.getTree(id);
        return JSON.toJSONString(zTreeNodeList);
    }
}
