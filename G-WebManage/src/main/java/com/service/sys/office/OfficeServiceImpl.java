package com.service.sys.office;

import com.mapper.table.EmployeeMapper;
import com.mapper.table.OfficeMapper;
import communal.Result;
import communal.util.LogUtil;
import model.entity.Employee;
import model.entity.Office;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class OfficeServiceImpl implements IOffice {

    @Autowired
    private OfficeMapper officeMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    private static Logger logger = LoggerFactory.getLogger(OfficeServiceImpl.class);

    /**
     * 保存
     * @param office
     * @param handlerType
     * @return
     */
    @Transactional
    public Result save(Office office, String handlerType) {

        try {
            int count = 0;
            String result = "机构保存成功!";
            if (handlerType.equals("add") || handlerType.equals("addChild")) {
                count = officeMapper.insertSelective(office);
            } else if (handlerType.equals("edit")) {
                count = officeMapper.updateByPrimaryKey(office);
            }
            return new Result(true, result, office);
        } catch (Exception e) {
            LogUtil.error(logger);
            return new Result(false, "机构保存失败:数据库异常!");
        }
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @Transactional
    public Result delete(Integer id) {

        Office office = null;
        {
            try {
                office = officeMapper.selectByPrimaryKey(id);
            } catch (Exception e) {
                LogUtil.error(logger);
                return new Result(false, "机构删除失败:数据库异常!");
            }

            if (StringUtils.isEmpty(office))
                return new Result(false, "该机构不存在!");
        }

        int count = 0;
        {
            try {
                Example example = new Example(Office.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("parentID", office.getOfficeID());
                count = officeMapper.selectCountByExample(example);
            } catch (Exception e) {
                LogUtil.error(logger);
                return new Result(false, "机构删除失败:数据库异常!");
            }

            if (count >= 1) {
                return new Result(false, "该机构下还有子节点, 不可被删除!");
            }
        }

        //判断该机构下是否有用户
        {
            try {
                Example example = new Example(Employee.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("officeID", office.getOfficeID());
                count = employeeMapper.selectCountByExample(example);
            } catch (Exception e) {
                LogUtil.error(logger);
                return new Result(false, "机构删除失败:数据库异常!");
            }

            if (count >= 1) {
                return new Result(false, "该机构下还有员工, 不可被删除!");
            }
        }

        try {
            count = officeMapper.deleteByPrimaryKey(id);
            return new Result(true, "机构删除成功!", count);
        } catch (Exception e) {
            LogUtil.error(logger);
            return new Result(false, "机构删除失败:数据库异常!");
        }
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

        Office office = null;
        try {
            office = officeMapper.selectByPrimaryKey(id);
        } catch (Exception e) {
            LogUtil.error(logger);
            return new Result(false, "返回机构失败:数据库异常!");
        }

        if (null != office) {
            return new Result(true, "查询成功!", office);
        } else {
            return new Result(false, "没有找到相应记录!", id);
        }
    }

    /**
     * 列表
     * @return
     */
    public List<Office> getList() {
        return officeMapper.selectAll();
    }
}
