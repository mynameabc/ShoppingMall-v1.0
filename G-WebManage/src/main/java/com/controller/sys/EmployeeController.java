package com.controller.sys;

import auxiliary.loginObject.GuaranteeAuthorization;
import com.alibaba.fastjson.JSON;
import com.service.sys.employee.IEmployee;
import communal.Result;
import communal.util.RegexUtil;
import communal.util.param.ParameterUtil;
import model.dto.manage.EmployeeDTO;
import model.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sys.GlobalConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping(value = "sys/employee")
public class EmployeeController {

    @Autowired
    private IEmployee employeeService;

    /**
     * 跳转到表单页面
     * @return
     */
    @GetMapping(value = "employee-form")
    public String form() {
        return "modules/sys/employee/employee-form";
    }

    /**
     * 跳转到列表页面
     * @return
     */
    @GetMapping(value = "employee-list")
    public String employee_list(HttpServletRequest request) {

        HttpSession session = request.getSession();
        GuaranteeAuthorization guaranteeAuthorization =
                (GuaranteeAuthorization)session.getAttribute(GlobalConstants.Manage_SessionObject);



        return "modules/sys/employee/employee-list";
    }

    /**
     * 保存员工
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping(value = "save")
    public String save(HttpServletRequest request) {

        Long id = ParameterUtil.longCheck(request.getParameter("id"), null);
        String handlerType = ParameterUtil.stringCheck(request.getParameter("handlerType"), "add");
        String name = ParameterUtil.stringCheck(request.getParameter("name"), null);
        String email = ParameterUtil.stringCheck(request.getParameter("email"), null);
        String mobile = ParameterUtil.stringCheck(request.getParameter("mobile"), null);
        String pws = ParameterUtil.stringCheck(request.getParameter("pws"), null);
        String jobNumber = ParameterUtil.stringCheck(request.getParameter("jobNumber"), null);
        String realName = ParameterUtil.stringCheck(request.getParameter("realName"), null);
        Integer officeID = ParameterUtil.integerCheck(request.getParameter("officeID"), 0);
        Boolean sex = ParameterUtil.booleanCheck(request.getParameter("sex"));
        Boolean loginLock = ParameterUtil.booleanCheck(request.getParameter("loginLock"));
        Boolean delFlag = ParameterUtil.booleanCheck(request.getParameter("delFlag"), true);
        String photoValue = ParameterUtil.stringCheck(request.getParameter("photoValue"), "../../static/images/defaultPhoto.jpg");

        Employee employee = null;
        if (handlerType.equals("add")) {
            employee = new Employee();
            employee.setCreateTime(new Date());
        } else {
            employee = (Employee)employeeService.get(id).getData();
        }

        employee.setName(name);
        employee.setEmail(email);
        employee.setMobile(mobile);
        employee.setPws(pws);
        employee.setJobNumber(jobNumber);
        employee.setRealName(realName);
        employee.setOfficeID(officeID);
        employee.setSex(sex);
        employee.setPhoto(photoValue);
        employee.setLoginLock(loginLock);
        employee.setDelFlag(delFlag);
        return JSON.toJSONString(employeeService.save(employee, handlerType));
    }

    /**
     * 删除员工(非真实删除)
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping(value = "del")
    public String del(HttpServletRequest request) {
        String id = request.getParameter("id");
        if (null == id) {
            return JSON.toJSONString(new Result(false, "请选择要删除的记录!"));
        } else {

            String [] parIDS = id.split(",");
            Long [] newIDS = new Long[parIDS.length];
            for (int index = 0; index < newIDS.length; index++) {
                newIDS[index] = Long.valueOf(parIDS[index]);
            }

            return JSON.toJSONString(employeeService.delete(newIDS));
        }
    }

    /**
     * 根据id返回员工
     * @param request
     * @return
     */
    @ResponseBody
    @GetMapping(value = "get")
    public String get(HttpServletRequest request) {
        String id = request.getParameter("id");if (null == id) {return null;}
        return JSON.toJSONString(employeeService.get(Long.valueOf(id)));
    }

    /**
     * 分页查询
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping(value = "getEmployeePagination")
    public String getEmployeePagination(HttpServletRequest request, HttpServletResponse response) {

        Integer limit = ParameterUtil.integerCheck(request.getParameter("limit"), 15);
        Integer offset = ParameterUtil.integerCheck(request.getParameter("offset"), 0);
        String account = ParameterUtil.stringCheck(request.getParameter("account"), null);
        String jobNumber = ParameterUtil.stringCheck(request.getParameter("jobNumber"), null);
        String realName = ParameterUtil.stringCheck(request.getParameter("realName"), null);
        String officeID = ParameterUtil.stringCheck(request.getParameter("officeID"), null);
        Integer delFlag = ParameterUtil.integerCheck(request.getParameter("delFlag"), -1);
        Integer loginLock = ParameterUtil.integerCheck(request.getParameter("loginLock"), -1);

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

        return employeeService.pagination(offset, limit, employeeDTO);
    }

    @GetMapping(value = "/exportExcel")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) {

        String account = ParameterUtil.stringCheck(request.getParameter("account"), null);
        String jobNumber = ParameterUtil.stringCheck(request.getParameter("jobNumber"), null);
        String realName = ParameterUtil.stringCheck(request.getParameter("realName"), null);
        String officeID = ParameterUtil.stringCheck(request.getParameter("officeID"), null);
        Integer delFlag = ParameterUtil.integerCheck(request.getParameter("delFlag"), -1);
        Integer loginLock = ParameterUtil.integerCheck(request.getParameter("loginLock"), -1);

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

        try {
            employeeService.exportExcel(employeeDTO, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
