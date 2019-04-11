package com.service.sys.role;

import communal.Result;
import model.dto.manage.EmployeeDTO;
import model.dto.manage.RoleDTO;
import model.entity.Role;
import model.entity.extend.EmployeeExtend;

import java.util.List;

public interface IRole {

    /**
     * 保存
     * @param role
     * @param handlerType
     * @return
     */
    Result save(Role role, String handlerType);

    /**
     * 删除
     * @param id
     * @return
     */
    Result delete(Integer id);

    /**
     * 根据id返回查询
     * @param id
     * @return
     */
    Result get(Integer id);

    /**
     * 列表
     * @return
     */
    List<Role> getList();

    /**
     * 分页
     * @param offset
     * @param limit
     * @param roleDTO
     * @return
     */
    String pagination(Integer offset, Integer limit, RoleDTO roleDTO);

    /**
     * 返回员工列表-未分配-分页
     * @param offset
     * @param limit
     * @param roleID
     * @param employeeDTO
     * @return
     */
    String getRoleEmployeeListForUndistributed(Integer offset, Integer limit, Integer roleID, EmployeeDTO employeeDTO);

    /**
     * 返回员工列表-已分配
     * @param roleID
     * @return
     */
    List<EmployeeExtend> getRoleEmployeeListForAssigned(Integer roleID);

    /**
     * 为添加角色人员
     * @param roleID
     * @param IDS
     * @return
     */
    Result addRoleEmployees(Integer roleID, String IDS);

    /**
     * 删除角色与员工的关系
     * @param roleID
     * @param employeeID
     * @return
     */
    Result delRoleEmployee(Integer roleID, Long employeeID);

    /**
     * 根据角色ID返回资源ID列表
     * @param roleID
     * @return
     */
    List<Integer> getResourcesIDSForRoleID(Integer roleID);

    /**
     * 保存角色的权限
     * @param roleID
     * @param IDS
     * @return
     */
    Result saveRoleResource(Integer roleID, String[] IDS);
}
