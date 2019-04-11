package com.service.member.detailed;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mapper.ExtendRechargeMapper;
import com.mapper.table.RechargeMapper;
import communal.Result;
import communal.util.LogUtil;
import model.dto.RechargeDTO;
import model.entity.extend.RechargePagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class RechargeServiceImpl implements IRechargeService {

    @Autowired
    private RechargeMapper rechargeMapper;

    @Autowired
    private ExtendRechargeMapper extendRechargeMapper;

    private static Logger logger = LoggerFactory.getLogger(RechargeServiceImpl.class);

    /**
     * 充值
     * @param memberID
     * @param money
     * @return
     */
    public Result recharge(Long memberID, BigDecimal money) {
        return new Result(true, "充值成功!", null);
    }

    /**
     * 分页查询
     * @param offset
     * @param limit
     * @param rechargeDTO
     * @return
     */
    public String getRechargePagination(Integer offset, Integer limit, RechargeDTO rechargeDTO) {

        PageHelper.offsetPage(offset, limit);
        Page<RechargePagination> dataList = null;

        try {
            dataList =
                    (Page<RechargePagination>)extendRechargeMapper.getPaginationList(rechargeDTO);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.error(logger);
        }

        JSONObject result = new JSONObject();
        result.put("rows", ((null != dataList) ? (dataList.getResult()) : (null)));
        result.put("total", ((null != dataList) ? (dataList.getTotal()) : (null)));
        return result.toJSONString();
    }
}
