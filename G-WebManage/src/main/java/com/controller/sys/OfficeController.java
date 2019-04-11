package com.controller.sys;

import com.alibaba.fastjson.JSON;
import com.service.sys.office.IOffice;
import model.entity.Office;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@Controller
@RequestMapping(value = "sys/office")
public class OfficeController {

    @Autowired
    private IOffice officeService;

    @GetMapping(value = "office-list")
    public String office_list() {
        return "modules/sys/office/office-list";
    }

    @GetMapping(value = "office-form")
    public String office_form() {
        return "modules/sys/office/office-form";
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
        String parentID = request.getParameter("officeID");
        String areaID = request.getParameter("areaID");
        String name = request.getParameter("name");
        String sort = request.getParameter("sort");
        String type = request.getParameter("type");
        String address = request.getParameter("address");
        String zipCode = request.getParameter("zipCode");
        String master = request.getParameter("master");
        String phone = request.getParameter("phone");
        String fax = request.getParameter("fax");
        String email = request.getParameter("email");
        String useable = request.getParameter("useable");
        String remarks = request.getParameter("remarks");
        String handlerType = request.getParameter("handlerType");

        Office office = new Office();
        if (handlerType.equals("edit")) {office.setOfficeID(Integer.valueOf(id));}        //如果是编辑就赋予主键值
        if (!StringUtils.isEmpty(parentID)) {office.setParentID(Integer.valueOf(parentID));}else{office.setParentID(0);}
        if (!StringUtils.isEmpty(areaID)) {office.setAreaID(areaID);}else{office.setAreaID(null);}
        office.setName(name);
        office.setSort(new BigDecimal(sort));
        office.setType(Integer.valueOf(type));
        office.setAddress(address);
        office.setZipCode(zipCode);
        office.setMaster(master);
        office.setPhone(phone);
        office.setFax(fax);
        office.setEmail(email);
        if (null == useable) {office.setUseable(false);} else {office.setUseable(true);}
        office.setRemarks(remarks);

        return JSON.toJSONString(officeService.save(office, handlerType));
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
        return JSON.toJSONString(officeService.delete(Integer.valueOf(id)));
    }

    /**
     * 根据id返回字典
     * @param request
     * @return
     */
    @ResponseBody
    @GetMapping(value = "get")
    public String get(HttpServletRequest request) {
        String id = request.getParameter("id");
        return JSON.toJSONString(officeService.get(Integer.valueOf(id)));
    }

    @ResponseBody
    @GetMapping(value = "getOfficeList")
    public String getOfficeList() {
        return JSON.toJSONString(officeService.getList());
    }
}
