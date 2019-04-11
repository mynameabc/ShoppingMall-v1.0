package com.controller.sys;

import com.alibaba.fastjson.JSON;
import com.service.sys.dictionary.IDictionary;
import model.entity.Dictionary;
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
@RequestMapping(value = "sys/dictionary")
public class DictionaryController {

    @Autowired
    private IDictionary dictionaryService;

    /**
     * 跳转到表单页面
     * @return
     */
    @GetMapping(value = "dictionary-form")
    public String form() {return "modules/sys/dictionary/dictionary-form";}

    /**
     * 跳转到列表页面
     * @return
     */
    @GetMapping(value = "dictionary-list")
    public String dictionary_list(HttpServletRequest request) {
        System.out.println(request.getServletPath());
        return "modules/sys/dictionary/dictionary-list";
    }

    /**
     * 保存字典
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping(value = "save")
    public String save(HttpServletRequest request) {

        String id = request.getParameter("id");
        String lable = request.getParameter("lable");
        String value = request.getParameter("value");
        String type = request.getParameter("type");
        String sort = request.getParameter("sort");
        String description = request.getParameter("description");
        String handlerType = request.getParameter("handlerType");

        Dictionary dictionary = new Dictionary();
        if (handlerType.equals("edit")) {dictionary.setId(Long.valueOf(id));}        //如果是编辑就赋予主键值
        dictionary.setLable(lable.trim());
        dictionary.setValue(value);
        dictionary.setType(type.trim());
        dictionary.setSort(new BigDecimal(sort));
        dictionary.setDescription(description);

        return JSON.toJSONString(dictionaryService.save(dictionary, handlerType));
    }

    /**
     * 删除字典
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping(value = "del")
    public String del(HttpServletRequest request) {
        String id = request.getParameter("id");if (null == id) {return null;}
        return JSON.toJSONString(dictionaryService.delete(Long.valueOf(id)));
    }

    /**
     * 根据id返回字典
     * @param request
     * @return
     */
    @ResponseBody
    @GetMapping(value = "get")
    public String get(HttpServletRequest request) {
        String id = request.getParameter("id");if (null == id) {return null;}
        return JSON.toJSONString(dictionaryService.get(Long.valueOf(id)));
    }

    /**
     * 分页查询
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping(value = "getDictionaryPagination")
    public String getDictionaryPagination(HttpServletRequest request) {
        Integer limit = Integer.parseInt(request.getParameter("limit"));
        Integer offset = Integer.parseInt(request.getParameter("offset"));
        String type = request.getParameter("type");
        return dictionaryService.pagination(offset, limit, type);
    }

    /**
     * 根据type查询相应的字典数据
     * @param request
     * @return
     */
    @ResponseBody
    @GetMapping(value = "getDictionaryListForType")
    public List<Dictionary> getDictionaryListForType(HttpServletRequest request) {
        String type = request.getParameter("type");if (null == type) {return null;}
        return dictionaryService.getDictionaryListForType(type);
    }
}
