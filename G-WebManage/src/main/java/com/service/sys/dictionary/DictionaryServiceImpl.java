package com.service.sys.dictionary;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mapper.table.DictionaryMapper;
import communal.Result;
import communal.util.LogUtil;
import model.entity.Dictionary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class DictionaryServiceImpl implements IDictionary {

    private static Logger logger = LoggerFactory.getLogger(DictionaryServiceImpl.class);

    @Autowired
    private DictionaryMapper dictionaryMapper;

    /**
     * 保存字典
     * @param dictionary
     * @param handlerType
     * @return
     */
    @Transactional
    public Result save(Dictionary dictionary, String handlerType) {

        try {
            int count = 0;
            String result = "";
            if (handlerType.equals("add")) {
                count = dictionaryMapper.insertSelective(dictionary);
                result = "新建成功!";
            } else if (handlerType.equals("edit")) {
                count = dictionaryMapper.updateByPrimaryKey(dictionary);
                result = "修改成功!";
            }
            return new Result(true, result, count);
        } catch (Exception e) {
            LogUtil.error(logger);
            e.printStackTrace();
            return new Result(false, "保存失败:数据库异常!");
        }
    }

    /**
     * 删除字典
     * @param id
     * @return
     */
    @Transactional
    public Result delete(Long id) {
        try {
            int count = dictionaryMapper.deleteByPrimaryKey(id);
            return new Result(true, "字典删除成功!", count);
        } catch (Exception e) {
            LogUtil.error(logger);
            return new Result(false, "修改失败:数据库异常!");
        }
    }

    /**
     * 根据id返回查询
     * @param id
     * @return
     */
    public Result get(Long id) {
        Dictionary dictionary = null;
        try {
            dictionary = dictionaryMapper.selectByPrimaryKey(id);
        } catch (Exception e) {
            LogUtil.error(logger);
            return new Result(false, "返回字典失败:数据库异常!");
        }
        return new Result(true, "查询成功!", dictionary);
    }

    /**
     * 分页
     * @param offset
     * @param limit
     * @param type
     * @return
     */
    public String pagination(Integer offset, Integer limit, String type) {

        PageHelper.offsetPage(offset, limit);
        Page<Dictionary> dictionaryList = (Page<Dictionary>)this.getDictionaryList(type);

        JSONObject result = new JSONObject();
        result.put("rows", ((null != dictionaryList) ? (dictionaryList.getResult()) : (null)));
        result.put("total", ((null != dictionaryList) ? (dictionaryList.getTotal()) : (null)));
        return result.toJSONString();
    }

    /**
     * 根据type查询字典
     * @param type
     * @return
     */
    public List<Dictionary> getDictionaryListForType(String type) {
        return this.getDictionaryList(type);
    }


    private List<Dictionary> getDictionaryList(String type) {

        List<Dictionary> dictionaryList = null;
        try {
            Example example = new Example(Dictionary.class);
            Example.Criteria criteria = example.createCriteria();
            {
                if (null != type && type.trim().length() >= 1) {criteria.andEqualTo("type", type.trim());}
            }
            example.orderBy("sort");
            dictionaryList = dictionaryMapper.selectByExample(example);
        } catch (Exception e) {
            LogUtil.error(logger);
        }
        return dictionaryList;
    }
}
