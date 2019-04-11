package com.controller.productCategory;

import com.alibaba.fastjson.JSON;
import communal.Result;
import communal.util.param.ParameterUtil;
import model.entity.ProductCategory;
import module.core.productCategory.IProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping(value = "productCategory")
public class ProductCategoryController {

    @Autowired
    private IProductCategoryService defaultSrvice;

    /**
     * 跳转到表单页面
     * @return
     */
    @GetMapping(value = "form")
    public String form() {
        return "modules/productCategory/productCategory-form";
    }

    /**
     * 跳转到列表页面
     * @return
     */
    @GetMapping(value = "list")
    public String list() {
        return "modules/productCategory/productCategory-list";
    }

    /**
     * 选择商品分类
     * @return
     */
    @GetMapping(value = "select-productCategory")
    public String select_productCategory() {
        return "modules/productCategory/select-productCategory";
    }

    /**
     * 保存商品分类
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping(value = "save")
    public String save(HttpServletRequest request) {

        String      handlerType =   ParameterUtil.stringCheck(request.getParameter("handlerType"), "add");
        Integer     id          =   ParameterUtil.integerCheck(request.getParameter("id"), null);
        String      name        =   ParameterUtil.stringCheck(request.getParameter("name"), "");
        Integer     parentID    =   ParameterUtil.integerCheck(request.getParameter("parentID"), 0);
        BigDecimal  sort        =   ParameterUtil.bigDecimalCheck(request.getParameter("sort"), "1.11");
        Boolean     showFlag    =   ParameterUtil.booleanCheck(request.getParameter("showFlag"), false);
        String      icon_1      =   ParameterUtil.stringCheck(request.getParameter("icon_1"), null);
        String      icon_2      =   ParameterUtil.stringCheck(request.getParameter("icon_2"), null);
        String      icon_3      =   ParameterUtil.stringCheck(request.getParameter("icon_3"), null);

        ProductCategory productCategory = new ProductCategory();
        if (handlerType.equals("edit")) {productCategory.setCategoryID(id);}        //如果是编辑就赋予主键值
        productCategory.setName(name);
        productCategory.setParentID(parentID);
        productCategory.setSort(sort);
        productCategory.setShowFlag(showFlag);
        productCategory.setIcon_1(icon_1);
        productCategory.setIcon_2(icon_2);
        productCategory.setIcon_3(icon_3);
        return JSON.toJSONString(defaultSrvice.save(productCategory, handlerType));
    }

    /**
     * 删除资源
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping(value = "del")
    public String del(HttpServletRequest request) {
        Integer id = ParameterUtil.integerCheck(request.getParameter("id"), null);
        return JSON.toJSONString(defaultSrvice.delete(Integer.valueOf(id)));
    }

    /**
     * 根据id返回菜单
     * @param request
     * @return
     */
    @ResponseBody
    @GetMapping(value = "get")
    public Result get(HttpServletRequest request) {
        Integer id = ParameterUtil.integerCheck(request.getParameter("id"), null);
        return defaultSrvice.get(Integer.valueOf(id));
    }

    /**
     * 列表
     * @return
     */
    @ResponseBody
    @GetMapping(value = "getProductCategoryList")
    public List<ProductCategory> getResourcesList() {
        return defaultSrvice.getList();
    }
}
