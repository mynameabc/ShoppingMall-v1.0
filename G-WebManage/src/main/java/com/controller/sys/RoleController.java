package com.controller.sys;

import com.alibaba.fastjson.JSON;
import com.service.sys.role.IRole;
import communal.Result;
import communal.util.RegexUtil;
import communal.util.param.ParameterUtil;
import model.dto.manage.EmployeeDTO;
import model.dto.manage.RoleDTO;
import model.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@Controller
@RequestMapping(value = "sys/role")
public class RoleController {

    @Autowired
    private IRole roleService;

    /**
     * 弹出表单页面
     * @return
     */
    @GetMapping(value = "role-form")
    public String role_form() {
        return "modules/sys/role/role-form";
    }

    /**
     * 跳转到列表页面
     * @return
     */
    @GetMapping(value = "role-list")
    public String role_list() {
        return "modules/sys/role/role-list";
    }

    /**
     * 弹出选择角色员工列表
     * @return
     */
    @GetMapping(value = "select-employee")
    public String select_employee() {
        return "modules/sys/role/select-employee";
    }

    /**
     * 弹出选择权限
     * @return
     */
    @GetMapping(value = "select-permission")
    public String select_permission() {return "modules/sys/role/select-permission";}

    /**
     * 保存
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping(value = "save")
    public String save(HttpServletRequest request) {

        Integer id = ParameterUtil.integerCheck(request.getParameter("id"), null);
        String handlerType = ParameterUtil.stringCheck(request.getParameter("handlerType"), "add");
        String name = ParameterUtil.stringCheck(request.getParameter("name"), null);
        Boolean validFlag = ParameterUtil.booleanCheck(request.getParameter("validFlag"));
        BigDecimal sort = ParameterUtil.bigDecimalCheck(request.getParameter("sort"), "0.00");
        String remark = ParameterUtil.stringCheck(request.getParameter("remark"), null);

        Role role = new Role();
        if (handlerType.equals("edit")) {role.setRoleID(id);}
        role.setName(name);
        role.setValidFlag(validFlag);
        role.setSort(sort);
        role.setRemark(remark);
        return JSON.toJSONString(roleService.save(role, handlerType));
    }

    /**
     * 删除角色
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping(value = "del")
    public String del(HttpServletRequest request) {
        Integer id = ParameterUtil.integerCheck(request.getParameter("id"), null);
        return JSON.toJSONString(roleService.delete(id));
    }

    /**
     * 根据id返回角色
     * @param request
     * @return
     */
    @ResponseBody
    @GetMapping(value = "get")
    public String get(HttpServletRequest request) {
        String id = request.getParameter("id");if (null == id) {return null;}
        return JSON.toJSONString(roleService.get(Integer.valueOf(id)));
    }

    @ResponseBody
    @GetMapping(value = "getList")
    public String getList() {
        return JSON.toJSONString(roleService.getList());
    }

    /**
     * 分页查询
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping(value = "getRolePagination")
    public String getDictionaryPagination(HttpServletRequest request) {

        Integer limit = ParameterUtil.integerCheck(request.getParameter("limit"), 0);
        Integer offset = ParameterUtil.integerCheck(request.getParameter("offset"), 15);
        Integer validFlag = ParameterUtil.integerCheck(request.getParameter("validFlag"), -1);

        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setValidFlag(validFlag);
        return roleService.pagination(offset, limit, roleDTO);
    }

    /**
     * 返回员工列表-未分配-分页
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping(value = "getRoleEmployeeListForUndistributed")
    public String getRoleEmployeeListForUndistributed(HttpServletRequest request) {

        Integer limit = ParameterUtil.integerCheck(request.getParameter("limit"), 0);
        Integer offset = ParameterUtil.integerCheck(request.getParameter("offset"), 10);
        String account = ParameterUtil.stringCheck(request.getParameter("account"), null);
        String jobNumber = ParameterUtil.stringCheck(request.getParameter("jobNumber"), null);
        String realName = ParameterUtil.stringCheck(request.getParameter("realName"), null);
        String officeID = ParameterUtil.stringCheck(request.getParameter("officeID"), null);
        Integer delFlag = ParameterUtil.integerCheck(request.getParameter("delFlag"), -1);
        Integer loginLock = ParameterUtil.integerCheck(request.getParameter("loginLock"), -1);
        Integer roleID = ParameterUtil.integerCheck(request.getParameter("roleID"), 0);

        EmployeeDTO employeeDTO = new EmployeeDTO();
        if (null != account && account.length() >= 1) {

            if (RegexUtil.mobile(account)) {
                employeeDTO.setMobile(account);
            } else if (RegexUtil.email(account)) {
                employeeDTO.setEmail(account);
            } else {
                employeeDTO.setName(account);
            }
        }

        employeeDTO.setJobNumber(jobNumber);
        employeeDTO.setRealName(realName);
        employeeDTO.setOfficeID(officeID);
        employeeDTO.setLoginLock(loginLock);
        employeeDTO.setDelFlag(delFlag);

        return roleService.getRoleEmployeeListForUndistributed(offset, limit, roleID, employeeDTO);
    }

    /**
     * 返回员工列表-已分配
     * @param request
     * @return
     */
    @ResponseBody
    @GetMapping(value = "getRoleEmployeeListForAssigned")
    public String getRoleEmployeeListForAssigned(HttpServletRequest request) {
        Integer roleID = ParameterUtil.integerCheck(request.getParameter("roleID"), null);
        return JSON.toJSONString(roleService.getRoleEmployeeListForAssigned(roleID));
    }

    /**
     * 为添加角色人员
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping(value = "addRoleEmployees")
    public String addRoleEmployees(HttpServletRequest request) {
        String IDS = ParameterUtil.stringCheck(request.getParameter("IDS"), null);
        Integer roleID = ParameterUtil.integerCheck(request.getParameter("roleID"), 0);
        return JSON.toJSONString(roleService.addRoleEmployees(roleID, IDS));
    }

    /**
     * 删除角色与员工的关系
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping(value = "delRoleEmployee")
    public String delRoleEmployee(HttpServletRequest request) {
        Integer roleID = ParameterUtil.integerCheck(request.getParameter("roleID"), 0);
        Long employeeID = ParameterUtil.longCheck(request.getParameter("employeeID"), 0L);
        return JSON.toJSONString(roleService.delRoleEmployee(roleID, employeeID));
    }

    /**
     * 根据角色ID返回资源ID列表
     * @param roleID
     * @return
     */
    @ResponseBody
    @GetMapping(value = "getResourcesIDSForRoleID")
    public String getResourcesIDSForRoleID(Integer roleID) {
        return JSON.toJSONString(roleService.getResourcesIDSForRoleID(roleID));
    }

    /**
     * 保存角色的权限
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping(value = "saveRoleResource")
    public String saveRoleResource(HttpServletRequest request) {
        String IDS = ParameterUtil.stringCheck(request.getParameter("IDS"), null);
        Integer roleID = ParameterUtil.integerCheck(request.getParameter("roleID"), 0);
        if (null == IDS){return JSON.toJSONString(new Result(false, "IDS不可为空!"));}
        if (0 == roleID){return JSON.toJSONString(new Result(false, "roleID不可为空!"));}
        return JSON.toJSONString(roleService.saveRoleResource(roleID, IDS.split(",")));
    }
}
