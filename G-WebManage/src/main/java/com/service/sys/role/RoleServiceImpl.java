package com.service.sys.role;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mapper.ExtendRoleMapper;
import com.mapper.table.RoleEmployeeMapper;
import com.mapper.table.RoleMapper;
import com.mapper.table.RoleResourcesMapper;
import com.service.member.MemberManage;
import communal.Result;
import communal.util.LogUtil;
import communal.util.UUIDGeneratorUtil;
import model.dto.manage.EmployeeDTO;
import model.dto.manage.RoleDTO;
import model.entity.Role;
import model.entity.extend.EmployeeExtend;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

@Service
public class RoleServiceImpl implements IRole {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private ExtendRoleMapper roleEmployeeStorageMapper;

    @Autowired
    private RoleEmployeeMapper roleEmployeeMapper;

    @Autowired
    private RoleResourcesMapper roleResourcesMapper;

    private static Logger logger = LogManager.getLogger(RoleServiceImpl.class);

    /**
     * 保存
     * @param role
     * @param handlerType
     * @return
     */
    public Result save(Role role, String handlerType) {

        int count = 0;
        String result = "角色保存成功!";

        try {

            if (handlerType.equals("add")) {
                role.setCreateTime(new Date());
                count = roleMapper.insertSelective(role);
            } else if (handlerType.equals("edit")) {
                Role _role = roleMapper.selectByPrimaryKey(role.getRoleID());
                _role.setName(role.getName());
                _role.setValidFlag(role.getValidFlag());
                _role.setSort(role.getSort());
                _role.setRemark(role.getRemark());
                count = roleMapper.updateByPrimaryKey(_role);
            }

        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.error(logger);
            return new Result(false, "角色保存失败:数据库异常!");
        }

        return (count >= 1) ? (new Result(true, result, role)) : (new Result(false, "角色保存失败!", role));
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @Transactional
    public Result delete(Integer id) {
        if (StringUtils.isEmpty(id)) {
            return new Result(false, "id参数为空!");
        }

        Role role = null;
        {
            try {
                role = roleMapper.selectByPrimaryKey(id);
            } catch (Exception e) {
                LogUtil.error(logger);
                return new Result(false, "角色删除失败:数据库异常!");
            }

            if (StringUtils.isEmpty(role))
                return new Result(false, "该角色不存在!");
        }

        {
            try {
                roleResourcesMapper.deleteRoleResources(id);//根据角色ID解除 sys_role_resources表的角色与资源关系
                roleEmployeeMapper.deleteForRoleID(id);     //根据角色ID解除 sys_role_employee表的角色与员工关系
                roleMapper.deleteByPrimaryKey(role);        //删除角色
            } catch (Exception e) {
                LogUtil.error(logger);
                return new Result(false, "角色删除失败:数据库异常!");
            }
        }

        return new Result(true, "删除成功!");
    }

    /**
     * 根据id返回查询
     * @param id
     * @return
     */
    public Result get(Integer id) {

        if (StringUtils.isEmpty(id)) {
            return new Result(false, "id参数为空!");
        }

        Role role = null;
        try {
            role = roleMapper.selectByPrimaryKey(id);
        } catch (Exception e) {
            LogUtil.error(logger);
            return new Result(false, "返回角色失败:数据库异常!");
        }

        if (null != role) {
            return new Result(true, "查询成功!", role);
        } else {
            return new Result(false, "没有找到相应记录!", id);
        }
    }

    /**
     * 列表
     * @return
     */
    public List<Role> getList() {
        return roleMapper.selectAll();
    }

    /**
     * 分页
     * @param offset
     * @param limit
     * @param roleDTO
     * @return
     */
    public String pagination(Integer offset, Integer limit, RoleDTO roleDTO) {

        PageHelper.offsetPage(offset, limit);
        Page<Role> roleList = null;
        try {
            Example example = new Example(Role.class);
            Example.Criteria criteria = example.createCriteria();

            {
                //登陆锁
                if (roleDTO.getValidFlag() != -1) {criteria.andEqualTo("validFlag",roleDTO.getValidFlag());}
            }

            example.orderBy("sort");
            roleList = (Page<Role>)roleMapper.selectByExample(example);
        } catch (Exception e) {
            LogUtil.error(logger);
        }

        JSONObject result = new JSONObject();
        result.put("rows", ((null != roleList) ? (roleList.getResult()) : (null)));
        result.put("total", ((null != roleList) ? (roleList.getTotal()) : (null)));
        return result.toJSONString();
    }

    /**
     * 返回员工列表-未分配-分页
     * @param offset
     * @param limit
     * @param roleID
     * @param employeeDTO
     * @return
     */
    public String getRoleEmployeeListForUndistributed(Integer offset, Integer limit, Integer roleID, EmployeeDTO employeeDTO) {

        List officeIDSList = null;
        if (null != employeeDTO.getOfficeID()) {
            officeIDSList = Arrays.asList(employeeDTO.getOfficeID().split(","));
        }

        PageHelper.offsetPage(offset, limit);
        Page<EmployeeExtend> dataList = null;
        try {
            dataList = (Page<EmployeeExtend>)roleEmployeeStorageMapper.getRoleEmployeeListForUndistributed(roleID, employeeDTO, officeIDSList);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.error(logger);
        }

        JSONObject result = new JSONObject();
        result.put("rows", ((null != dataList) ? (dataList.getResult()) : (null)));
        result.put("total", ((null != dataList) ? (dataList.getTotal()) : (null)));
        return result.toJSONString();
    }

    /**
     * 返回员工列表-已分配
     * @param roleID
     * @return
     */
    public List<EmployeeExtend> getRoleEmployeeListForAssigned(Integer roleID) {
        return roleEmployeeStorageMapper.getRoleEmployeeListForAssigned(roleID);
    }

    /**
     * 为添加角色人员
     * @param roleID
     * @param IDS
     * @return
     */
    public Result addRoleEmployees(Integer roleID, String IDS) {

        String [] ids = IDS.split(",");

        try {
            roleEmployeeStorageMapper.addRoleEmployees(roleID, ids);
        } catch (Exception e) {
            LogUtil.error(logger);
            return new Result(false, "添加失败!");
        }
        return new Result(true, "添加成功!");
    }

    /**
     * 删除角色与员工的关系
     * @param roleID
     * @param employeeID
     * @return
     */
    public Result delRoleEmployee(Integer roleID, Long employeeID) {

        if (StringUtils.isEmpty(roleID)) {
            return new Result(false, "角色ID为空!");
        }

        if (StringUtils.isEmpty(employeeID)) {
            return new Result(false, "员工ID为空!");
        }

        try {
            roleEmployeeStorageMapper.delRoleEmployee(roleID, employeeID);
        } catch (Exception e) {
            LogUtil.error(logger);
            return new Result(false, "角色员工删除失败:数据库异常!");
        }

        return new Result(true, "删除成功!");
    }

    /**
     * 根据角色ID返回资源ID列表
     * @param roleID
     * @return
     */
    public List<Integer> getResourcesIDSForRoleID(Integer roleID) {

        List<Integer> integerList = null;
        try {
            integerList = roleResourcesMapper.getResourcesIDSForRoleID(roleID);
        } catch (Exception e) {
            LogUtil.error(logger);
        }

        return integerList;
    }

    /**
     * 保存角色的权限
     * @param roleID
     * @param IDS
     * @return
     */
    public Result saveRoleResource(Integer roleID, String [] IDS) {

        if (StringUtils.isEmpty(roleID)) {
            return new Result(false, "角色ID为空!");
        }

        Map map = new HashMap<String, Integer>();
        {
            //生成sys_role_resources表的主键ID, 用UUID
            for (int index = 0; index < IDS.length; index++) {
                map.put(UUIDGeneratorUtil.getUUID(), IDS[index]);
            }
        }

        Integer count = roleResourcesMapper.getCountForRoleID(roleID);
        if (0 == count) {

            //新增
            try {
                roleResourcesMapper.addRoleResources(roleID, map);
            } catch (Exception e) {
                logger.error(e.toString());
                return new Result(true, "保存角色权限成功!");
            }

        } else {

            //修改 (先删除再新增)
            try {
                roleResourcesMapper.deleteRoleResources(roleID);
            } catch (Exception e) {
                LogUtil.error(logger);
                return new Result(false, "角色删除失败:数据库异常!");
            }

            try {
                roleResourcesMapper.addRoleResources(roleID, map);
            } catch (Exception e) {
                LogUtil.error(logger);
                return new Result(false, "角色修改失败:数据库异常!");
            }
        }

        return new Result(true, "保存角色权限成功!");
    }
}
