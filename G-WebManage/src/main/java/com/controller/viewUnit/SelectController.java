package com.controller.viewUnit;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "common")
public class SelectController {

    /**
     * 选择图标
     * @return
     */
    @RequestMapping(value = "select-icons")
    public String select_icons() {
        return "modules/common/select-icons";
    }

    /**
     * 选择图片
     * @return
     */
    @GetMapping(value = "select-photo")
    public String select_photo() {
        return "modules/common/select-photo";
    }

    /**
     * 选择地区
     * @return
     */
    @RequestMapping(value = "select-area")
    public String select_area() {
        return "modules/common/select-area";
    }

    /**
     * 选择机构
     * @return
     */
    @GetMapping(value = "select-office")
    public String select_office() {
        return "modules/common/select-office";
    }

}
