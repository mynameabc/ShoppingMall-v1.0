package com.service.sys.dictionary;

import communal.Result;
import model.entity.Dictionary;

import java.util.List;

public interface IDictionary {

    /**
     * 保存字典
     * @param dictionary
     * @param handlerType
     * @return
     */
    Result save(Dictionary dictionary, String handlerType);

    /**
     * 删除字典
     * @param id
     * @return
     */
    Result delete(Long id);

    /**
     * 根据id返回查询
     * @param id
     * @return
     */
    Result get(Long id);

    /**
     * 根据type查询字典
     * @param type
     * @return
     */
    List<Dictionary> getDictionaryListForType(String type);

    /**
     * 分页
     * @param offset
     * @param limit
     * @param type
     * @return
     */
    String pagination(Integer offset, Integer limit, String type);
}
