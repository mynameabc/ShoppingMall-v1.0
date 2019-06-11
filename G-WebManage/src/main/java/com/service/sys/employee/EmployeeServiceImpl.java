package com.service.sys.employee;

import auxiliary.office.excel.utils.ExcelUtils;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mapper.ExtendEmployeeMapper;
import com.mapper.table.EmployeeMapper;
import communal.Result;
import communal.util.DateUtil;
import communal.util.LogUtil;
import model.dto.manage.EmployeeDTO;
import model.entity.Employee;
import model.entity.extend.EmployeeExtend;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class EmployeeServiceImpl implements IEmployee {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private ExtendEmployeeMapper employeeStorageMapper;

    private static Logger logger = LogManager.getLogger(EmployeeServiceImpl.class);

    /**
     * 保存
     * @param employee
     * @param handlerType
     * @return
     */
    @Transactional
    public Result save(Employee employee, String handlerType) {

        try {
            int count = 0;
            String result = "员工保存成功!";
            if (handlerType.equals("add")) {
                count = employeeMapper.insertSelective(employee);
            } else if (handlerType.equals("edit")) {
                count = employeeMapper.updateByPrimaryKey(employee);
            }
            return new Result(true, result, employee);
        } catch (Exception e) {
            LogUtil.error(logger);
            return new Result(false, "机构员工失败:数据库异常!", employee);
        }
    }

    /**
     * 删除
     * @param newIDS
     * @return
     */
    @Transactional
    public Result delete(Long [] newIDS) {

/*        Employee employee = null;
        {
            try {
                employee = employeeMapper.selectByPrimaryKey(employeeID);
            } catch (Exception e) {
                LogUtil.error(logger);
                return new Result(false, "员工删除失败:数据库异常!");
            }

            if (StringUtils.isEmpty(employee))
                return new Result(false, "该员工不存在!");
        }

        {
            try {
                employee.setDelFlag(false);
                employeeMapper.updateByPrimaryKeySelective(employee);
            } catch (Exception e) {
                LogUtil.error(logger);
                return new Result(false, "员工删除失败:数据库异常!");
            }
        }*/

        try {
            employeeMapper.deleteForIDS(newIDS);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.error(logger);
            return new Result(false, "员工删除失败:数据库异常!");
        }

        return new Result(true, "删除成功!");
    }

    /**
     * 根据id返回查询
     * @param employeeID
     * @return
     */
    public Result get(Long employeeID) {

        if (StringUtils.isEmpty(employeeID)) {
            return new Result(false, "id参数为空!");
        }

        Employee employee = null;
        try {
            employee = employeeMapper.selectByPrimaryKey(employeeID);
        } catch (Exception e) {
            LogUtil.error(logger);
            return new Result(false, "返回员工失败:数据库异常!");
        }

        if (null != employee) {
            return new Result(true, "查询成功!", employee);
        } else {
            return new Result(false, "没有找到相应记录!", employeeID);
        }
    }

    /**
     * 分页
     * @param offset
     * @param limit
     * @param employeeDTO
     * @return
     */
    /*
    public String pagination(Integer offset, Integer limit, EmployeeDTO employeeDTO) {

        PageHelper.offsetPage(offset, limit);
        Page<Employee> employeeList = null;
        try {
            Example example = new Example(Employee.class);
            Example.Criteria criteria = example.createCriteria();

            {
                //登陆呢称
                if (!StringUtils.isEmpty(employeeDTO.getName())) {criteria.andEqualTo("name",employeeDTO.getName());}

                //邮箱
                if (!StringUtils.isEmpty(employeeDTO.getEmail())) {criteria.andEqualTo("email",employeeDTO.getEmail());}

                //手机号码
                if (!StringUtils.isEmpty(employeeDTO.getMobile())) {criteria.andEqualTo("mobile",employeeDTO.getMobile());}

                //工号
                if (!StringUtils.isEmpty(employeeDTO.getJobNumber())) {criteria.andEqualTo("jobNumber",employeeDTO.getJobNumber());}

                //真实姓名
                if (!StringUtils.isEmpty(employeeDTO.getRealName())) {criteria.andEqualTo("realName",employeeDTO.getRealName());}

                //机构ID
                if (!StringUtils.isEmpty(employeeDTO.getOfficeID())) {criteria.andEqualTo("officeID",employeeDTO.getOfficeID());}

                //登陆锁
                if (employeeDTO.getLoginLock() != -1) {criteria.andEqualTo("loginLock",employeeDTO.getLoginLock());}

                //删除标识
                if (employeeDTO.getDelFlag() != -1) {criteria.andEqualTo("delFlag",employeeDTO.getDelFlag());}
            }

            example.orderBy("createTime");
            employeeList = (Page<Employee>)employeeMapper.selectByExample(example);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.error(logger);
        }

        JSONObject result = new JSONObject();
        result.put("rows", ((null != employeeList) ? (employeeList.getResult()) : (null)));
        result.put("total", ((null != employeeList) ? (employeeList.getTotal()) : (null)));
        return result.toJSONString();
    }
*/

    public String pagination(Integer offset, Integer limit, EmployeeDTO employeeDTO) {

        List officeIDSList = null;
        if (null != employeeDTO.getOfficeID()) {
            officeIDSList = Arrays.asList(employeeDTO.getOfficeID().split(","));
        }

        PageHelper.offsetPage(offset, limit);
        Page<EmployeeExtend> memberList =
                (Page<EmployeeExtend>)employeeStorageMapper.getEmployeeList(employeeDTO, officeIDSList);

        JSONObject result = new JSONObject();
        result.put("rows", memberList.getResult());
        result.put("total", memberList.getTotal());
        return result.toJSONString();
    }

    /**
     * 返回全部
     * @return
     */
    public List<Employee> getAllList() {
        return employeeMapper.selectAll();
    }

    /**
     * 导出excel
     */
    public void exportExcel(EmployeeDTO employeeDTO, HttpServletResponse response) throws Exception {

        List officeIDSList = null;
        if (null != employeeDTO.getOfficeID()) {
            officeIDSList = Arrays.asList(employeeDTO.getOfficeID().split(","));
        }

        List<EmployeeExtend> _tempList = employeeStorageMapper.getEmployeeList(employeeDTO, officeIDSList);;

        String[] titleNames = new String[]{"序列", "工号","所属机构","真实姓名","性别","手机号码","会员名","登陆锁","删除标识"};
        List<Object[]>  dataList = new ArrayList<Object[]>();

        Object[] objs = null;
        for (int i = 0; i < _tempList.size(); i++) {

            EmployeeExtend employeeExtend = _tempList.get(i);

            objs = new Object[titleNames.length];
            objs[0] = i + 1;
            objs[1] = employeeExtend.getJobNumber();
            objs[2] = employeeExtend.getOfficeName();
            objs[3] = employeeExtend.getRealName();

            if (employeeExtend.getSex()) {
                objs[4] = "男";
            } else {
                objs[4] = "女";
            }

            objs[5] = employeeExtend.getMobile();
            objs[6] = employeeExtend.getName();
//            objs[7] = DateUtil.dateFormatToString(employeeExtend.getCreateTime());

            if (employeeExtend.getLoginLock()) {
                objs[7] = "未锁";
            } else {
                objs[7] = "已锁";
            }

            if (employeeExtend.getDelFlag()) {
                objs[8] = "正常";
            } else {
                objs[8] = "已删除";
            }

            dataList.add(objs);
        }

        ExcelUtils excelUtils = new ExcelUtils("员工业绩报表-" + DateUtil.dateFormatToString(new Date()) + ".xls", response);
        excelUtils.createSheet("员工业绩报表", "员工业绩报表", titleNames, dataList);
        excelUtils.createSheet("员工业绩报表1", "员工业绩报表2", titleNames, dataList);
        excelUtils.close();
    }
}
