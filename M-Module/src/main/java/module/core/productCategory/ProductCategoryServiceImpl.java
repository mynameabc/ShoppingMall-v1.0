package module.core.productCategory;

import com.mapper.table.ProductCategoryMapper;
import communal.Result;
import communal.util.LogUtil;
import model.entity.Office;
import model.entity.ProductCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements IProductCategoryService {

    private static final String NAME = "商品分类";

    private static Logger logger = LoggerFactory.getLogger(ProductCategoryServiceImpl.class);

    @Autowired
    private ProductCategoryMapper defaultMapper;

    /**
     * 保存
     * @param productCategory
     * @param handlerType
     * @return
     */
    @Transactional
    public Result save(ProductCategory productCategory, String handlerType) {

        try {
            int count = 0;
            String result = this.NAME + "保存成功!";
            if (handlerType.equals("add") || handlerType.equals("addChild")) {
                count = defaultMapper.insertSelective(productCategory);
            } else if (handlerType.equals("edit")) {
                count = defaultMapper.updateByPrimaryKey(productCategory);
            }
            return new Result(true, result, count);
        } catch (Exception e) {
            LogUtil.error(logger);
            return new Result(false, this.NAME + "保存失败:数据库异常!");
        }
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @Transactional
    public Result delete(Integer id) {

        ProductCategory productCategory = null;
        {
            if (StringUtils.isEmpty(id))
                return new Result(false, this.NAME + "不存在!");

            try {
                productCategory = defaultMapper.selectByPrimaryKey(id);
            } catch (Exception e) {
                LogUtil.error(logger);
                return new Result(false, this.NAME + "删除失败:数据库异常!");
            }

            if (StringUtils.isEmpty(productCategory))
                return new Result(false, "该" + this.NAME + "不存在!");
        }

        int count = 0;
        {
            try {
                Example example = new Example(Office.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("parentID", productCategory.getCategoryID());
                count = defaultMapper.selectCountByExample(example);
            } catch (Exception e) {
                LogUtil.error(logger);
                return new Result(false, this.NAME + "删除失败:数据库异常!");
            }

            if (count >= 1) {
                return new Result(false, "该" + this.NAME + "下还有子节点, 不可被删除!");
            }
        }

        try {
            count = defaultMapper.deleteByPrimaryKey(id);
            return new Result(true, this.NAME + "删除成功!", count);
        } catch (Exception e) {
            LogUtil.error(logger);
            return new Result(false, this.NAME + "删除失败:数据库异常!");
        }
    }

    /**
     * 根据id返回对象
     * @param id
     * @return
     */
    public Result get(Integer id) {

        if (StringUtils.isEmpty(id))
            return new Result(false, "该" + this.NAME + "不存在!");

        ProductCategory productCategory = null;
        try {
            productCategory = defaultMapper.selectByPrimaryKey(id);
        } catch (Exception e) {
            LogUtil.error(logger);
            return new Result(false, "返回" + this.NAME + "失败:数据库异常!");
        }
        return new Result(true, "查询成功!", productCategory);
    }

    /**
     * 列表
     * @return
     */
    public List<ProductCategory> getList() {

        List<ProductCategory> resourcesList = null;
        try {
            Example example = new Example(ProductCategory.class);
            Example.Criteria criteria = example.createCriteria();
            example.orderBy("sort");
            resourcesList = defaultMapper.selectByExample(example);
        } catch (Exception e) {
            LogUtil.error(logger);
        }
        return resourcesList;
    }
}
