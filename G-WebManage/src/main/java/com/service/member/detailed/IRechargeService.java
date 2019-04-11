package com.service.member.detailed;

import communal.Result;
import model.dto.RechargeDTO;

import java.math.BigDecimal;

public interface IRechargeService {

    /**
     * 充值
     * @param memberID
     * @param money
     * @return
     */
    Result recharge(Long memberID, BigDecimal money);

    /**
     * 分页查询
     * @param offset
     * @param limit
     * @param rechargeDTO
     * @return
     */
    String getRechargePagination(Integer offset, Integer limit, RechargeDTO rechargeDTO);
}
