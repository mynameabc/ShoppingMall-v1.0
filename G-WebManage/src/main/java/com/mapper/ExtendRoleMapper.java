package com.mapper;

import model.dto.manage.EmployeeDTO;
import model.entity.extend.EmployeeExtend;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExtendRoleMapper {

    /**
     * 返回员工列表-未分配
     * @param roleID
     * @param employeeDTO
     * @param officeIDSList
     * @return
     */
    List<EmployeeExtend> getRoleEmployeeListForUndistributed(@Param("roleID") Integer roleID, @Param("employeeDTO") EmployeeDTO employeeDTO, @Param("officeIDSList") List<String> officeIDSList);

    /**
     * 返回员工列表-已分配
     * @param roleID
     * @return
     */
    List<EmployeeExtend> getRoleEmployeeListForAssigned(@Param("roleID") Integer roleID);

    /**
     * 为角色分配员工
     * @param roleID
     * @param IDS
     */
    void addRoleEmployees(@Param("roleID") Integer roleID, @Param("IDS") String[] IDS);

    /**
     * 删除角色与员工的关系
     * @param roleID
     * @param employeeID
     * @return
     */
    int delRoleEmployee(@Param("roleID") Integer roleID, @Param("employeeID") Long employeeID);
}
