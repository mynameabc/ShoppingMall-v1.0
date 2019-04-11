package com.service.manageLogin;

import auxiliary.loginObject.GuaranteeAuthorization;
import auxiliary.subassembly.tree.ztree.impl.ResourcesZTreeImpl;
import com.alibaba.fastjson.JSON;
import com.mapper.ManageLoginMapper;
import com.mapper.table.RoleEmployeeMapper;
import communal.Result;
import communal.util.LogUtil;
import model.dto.manage.ManageLoginDTO;
import model.entity.Employee;
import model.entity.Resources;
import model.vo.ZTreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManageLoginImpl implements IManageLogin {

    @Autowired
    private ManageLoginMapper manageLoginMapper;

    @Autowired
    private RoleEmployeeMapper roleEmployeeMapper;

    @Autowired
    @Qualifier("resourcesZTreeImpl")
    private ResourcesZTreeImpl resourcesZTreeImpl;

    private static Logger logger = LoggerFactory.getLogger(ManageLoginImpl.class);

    public Result login(ManageLoginDTO manageLoginDTO) {

        Employee employee = null;

        try {
            employee = manageLoginMapper.getEmployeeForLogin(manageLoginDTO);
        } catch (Exception e) {
            LogUtil.error(logger);
            return new Result(false, "后台登陆失败:数据库异常!");
        }

        if (null != employee) {

            List<Resources> resourcesList = manageLoginMapper.getResourcesForEmployeeID(1, employee.getEmployeeID());

            List<Resources> menuList = resourcesList.stream().filter(object -> object.getType() == 1).collect(Collectors.toList());
            List<Resources> buttonList = resourcesList.stream().filter(object -> object.getType() == 2).collect(Collectors.toList());

            List<ZTreeNode> zTreeNodeList = resourcesZTreeImpl.getMenuString(resourcesList);

            GuaranteeAuthorization guaranteeAuthorization = new GuaranteeAuthorization();
            guaranteeAuthorization.setEmployeeID(employee.getEmployeeID());
            guaranteeAuthorization.setEmployee(employee);
            guaranteeAuthorization.setMenuList(JSON.toJSONString(zTreeNodeList));

            return new Result(true, "后台登陆成功!", guaranteeAuthorization);
        } else {
            return new Result(false, "登陆或密码错误!");
        }
    }
}
