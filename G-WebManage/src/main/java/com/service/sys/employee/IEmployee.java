package com.service.sys.employee;

import communal.Result;
import model.dto.manage.EmployeeDTO;
import model.entity.Employee;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface IEmployee {

    /**
     * 保存
     * @param employee
     * @param handlerType
     * @return
     */
    Result save(Employee employee, String handlerType);

    /**
     * 删除
     * @param newIDS
     * @return
     */
    Result delete(Long [] newIDS);
/*    Result delete(Long employeeID);*/

    /**
     * 根据id返回查询
     * @param employeeID
     * @return
     */
    Result get(Long employeeID);

    /**
     * 分页
     * @param offset
     * @param limit
     * @param employeeDTO
     * @return
     */
    String pagination(Integer offset, Integer limit, EmployeeDTO employeeDTO);

    /**
     * 返回全部
     * @return
     */
    List<Employee> getAllList();

    /**
     * 导出excel
     */
    void exportExcel(EmployeeDTO employeeDTO, HttpServletResponse response) throws Exception;
}
