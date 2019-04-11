package com.mapper;

import com.MyMapper;
import model.dto.RechargeDTO;
import model.entity.extend.RechargePagination;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExtendRechargeMapper extends MyMapper<RechargePagination> {

    List<RechargePagination> getPaginationList(@Param("rechargeDTO") RechargeDTO rechargeDTO);

    List getList();
}
