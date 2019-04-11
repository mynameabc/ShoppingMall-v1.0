package com.service.sys.office;

import communal.Result;
import model.entity.Office;

import java.util.List;

public interface IOffice {

    /**
     * 保存
     * @param office
     * @param handlerType
     * @return
     */
    Result save(Office office, String handlerType);

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

    /**
     * 列表
     * @return
     */
    List<Office> getList();
}
