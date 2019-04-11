package com.mapper;

import model.dto.manage.ManageLoginDTO;
import model.entity.Employee;
import model.entity.Resources;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ManageLoginMapper {

    Employee getEmployeeForLogin(@Param("manageLoginDTO") ManageLoginDTO manageLoginDTO);

    /**
     * 根据员工ID返回权限列表
     * @param type 1表示菜单, 2表示按钮
     * @param employeeID
     * @return
     */
    List<Resources> getResourcesForEmployeeID(@Param("type") Integer type, @Param("employeeID") Long employeeID);
}
