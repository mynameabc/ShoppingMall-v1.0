package com.service.sys.resources;

import auxiliary.subassembly.tree.treeview.impl.OfficeTreeViewImpl;
import com.mapper.table.ResourcesMapper;
import communal.Result;
import communal.util.LogUtil;
import model.entity.Resources;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class ResourcesServiceImpl implements IResources {

    private static Logger logger = LogManager.getLogger(ResourcesServiceImpl.class);

    @Autowired
    private ResourcesMapper resourcesMapper;

    /**
     * 保存
     * @param resources
     * @param handlerType
     * @return
     */
    @Transactional
    public Result save(Resources resources, String handlerType) {

        try {
            int count = 0;
            String result = "资源保存成功!";
            if (handlerType.equals("add") || handlerType.equals("addChild")) {
                count = resourcesMapper.insertSelective(resources);
            } else if (handlerType.equals("edit")) {
                count = resourcesMapper.updateByPrimaryKey(resources);
            }
            return new Result(true, result, count);
        } catch (Exception e) {
            LogUtil.error(logger);
            return new Result(false, "资源保存失败:数据库异常!");
        }
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @Transactional
    public Result delete(Integer id) {

        Resources resources = null;
        {
            if (StringUtils.isEmpty(id))
                return new Result(false, "该资源不存在!");

            try {
                resources = resourcesMapper.selectByPrimaryKey(id);
            } catch (Exception e) {
                LogUtil.error(logger);
                return new Result(false, "资源删除失败:数据库异常!");
            }

            if (StringUtils.isEmpty(resources))
                return new Result(false, "该资源不存在!");
        }

        int count = 0;
        {
            try {
                Example example = new Example(Resources.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("parentID", resources.getResourcesID());
                count = resourcesMapper.selectCountByExample(example);
            } catch (Exception e) {
                LogUtil.error(logger);
                return new Result(false, "资源删除失败:数据库异常!");
            }

            if (count >= 1) {
                return new Result(false, "该资源下还有子节点, 不可被删除!");
            }
        }

        try {
            count = resourcesMapper.deleteByPrimaryKey(id);
            return new Result(true, "资源删除成功!", count);
        } catch (Exception e) {
            LogUtil.error(logger);
            return new Result(false, "资源删除失败:数据库异常!");
        }
    }

    /**
     * 根据id返回对象
     * @param id
     * @return
     */
    public Result get(Integer id) {

        if (StringUtils.isEmpty(id))
            return new Result(false, "该资源不存在!");

        Resources resources = null;
        try {
            resources = resourcesMapper.selectByPrimaryKey(id);
        } catch (Exception e) {
            LogUtil.error(logger);
            return new Result(false, "返回资源失败:数据库异常!");
        }
        return new Result(true, "查询成功!", resources);
    }

    /**
     * 列表
     * @return
     */
    public List<Resources> getList() {

        List<Resources> resourcesList = null;
        try {
            Example example = new Example(Resources.class);
            Example.Criteria criteria = example.createCriteria();
            example.orderBy("sort");
            resourcesList = resourcesMapper.selectByExample(example);
        } catch (Exception e) {
            LogUtil.error(logger);
        }
        return resourcesList;
    }
}
