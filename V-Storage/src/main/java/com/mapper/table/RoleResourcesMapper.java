package com.mapper.table;

import com.MyMapper;
import model.entity.RoleResources;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface RoleResourcesMapper extends MyMapper<RoleResources> {

    /**
     * 根据角色ID返回资源ID列表
     * @param roleID
     * @return
     */
    List<Integer> getResourcesIDSForRoleID(@Param("roleID") Integer roleID);

    /**
     * 查看相应角色是权限数量
     * @param roleID
     * @return
     */
    Integer getCountForRoleID(@Param("roleID") Integer roleID);

    /**
     * 新增角色的权限
     * @param roleID
     * @param IDS
     */
/*    void addRoleResources(@Param("roleID")Integer roleID, @Param("IDS")String [] IDS);*/

    /**
     * 新增角色的权限
     * @param roleID
     * @param IDMaps
     */
    void addRoleResources(@Param("roleID") Integer roleID, @Param("IDMaps") Map<String, Integer> IDMaps);

    /**
     * 根据角色ID删除角色
     * @param roleID
     * @return
     */
    void deleteRoleResources(@Param("roleID") Integer roleID);
}
