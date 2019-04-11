package com.mapper.table;

import com.MyMapper;
import model.entity.Resources;

import java.util.List;

public interface ResourcesMapper extends MyMapper<Resources> {

    /**
     * 菜单资源列表
     * @return
     */
    List<Resources> getMenuList();

    /**
     * 按钮资源列表
     * @return
     */
    List<Resources> getButtonList();
}
