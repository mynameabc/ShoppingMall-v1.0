package com.controller.sys;

import com.alibaba.fastjson.JSON;
import com.service.sys.resources.IResources;
import communal.Result;
import communal.util.param.ParameterUtil;
import model.entity.Resources;
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
@RequestMapping(value = "sys/resources")
public class ResourcesController {

    @Autowired
    private IResources resourcesService;

    /**
     * 跳转到表单页面
     * @return
     */
    @GetMapping(value = "form")
    public String form() {
        return "modules/sys/resources/resources-form";
    }

    /**
     * 跳转到列表页面
     * @return
     */
    @GetMapping(value = "list")
    public String list() {
        return "modules/sys/resources/resources-list";
    }

    /**
     * 选择资源
     * @return
     */
    @GetMapping(value = "select-resources")
    public String select_resources() {
        return "modules/sys/resources/select-resources";
    }

    /**
     * 保存菜单
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping(value = "save")
    public String save(HttpServletRequest request) {

        String      handlerType =      ParameterUtil.stringCheck(request.getParameter("handlerType"), "add");
        Integer     id          =      ParameterUtil.integerCheck(request.getParameter("id"), null);
        Integer     parentID    =      ParameterUtil.integerCheck(request.getParameter("parentID"), 0);
        String      icon        =      ParameterUtil.stringCheck(request.getParameter("icon"), null);
        String      title       =      ParameterUtil.stringCheck(request.getParameter("title"), null);
        BigDecimal  sort        =      ParameterUtil.bigDecimalCheck(request.getParameter("sort"), "1.11");
        String      href        =      ParameterUtil.stringCheck(request.getParameter("href"), null);
        Boolean     showFlag    =      ParameterUtil.booleanCheck(request.getParameter("showFlag"), false);
        Integer     type        =      ParameterUtil.integerCheck(request.getParameter("type"), 1);

        Resources resources = new Resources();
        if (handlerType.equals("edit")) {resources.setResourcesID(id);}        //如果是编辑就赋予主键值
        resources.setParentID(parentID);
        resources.setIcon(icon);
        resources.setTitle(title);
        resources.setSort(sort);
        resources.setHref(href);
        resources.setShowFlag(showFlag);
        resources.setType(type);
        return JSON.toJSONString(resourcesService.save(resources, handlerType));
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
        return JSON.toJSONString(resourcesService.delete(Integer.valueOf(id)));
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
        return resourcesService.get(Integer.valueOf(id));
    }

    /**
     * 列表
     * @return
     */
    @ResponseBody
    @GetMapping(value = "getResourcesList")
    public List<Resources> getResourcesList() {
        return resourcesService.getList();
    }
}
