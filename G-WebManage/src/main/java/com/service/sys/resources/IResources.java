package com.service.sys.resources;

import communal.Result;
import model.entity.Resources;

import java.util.List;

public interface IResources {

    /**
     * 保存
     * @param resources
     * @param handlerType
     * @return
     */
    Result save(Resources resources, String handlerType);

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

    List<Resources> getList();
}
