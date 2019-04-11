package module.core.productCategory;

import communal.Result;
import model.entity.ProductCategory;

import java.util.List;

public interface IProductCategoryService {

    /**
     * 保存
     * @param productCategory
     * @param handlerType
     * @return
     */
    Result save(ProductCategory productCategory, String handlerType);

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

    List<ProductCategory> getList();
}
