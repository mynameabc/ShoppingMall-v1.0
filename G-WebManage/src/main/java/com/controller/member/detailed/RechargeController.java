package com.controller.member.detailed;

import com.service.member.detailed.IRechargeService;
import communal.util.param.ParameterUtil;
import model.dto.RechargeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@RequestMapping(value = "member/recharge")
public class RechargeController {

    @Autowired
    private IRechargeService recharge;

    /**
     * 分页查询
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping(value = "getRechargePagination")
    public String getRechargePagination(HttpServletRequest request) {

        Integer limit = ParameterUtil.integerCheck(request.getParameter("limit"), 0);
        Integer offset = ParameterUtil.integerCheck(request.getParameter("offset"), 10);
        Long memberID = ParameterUtil.longCheck(request.getParameter("memberID"), 0L);
        Date startDate = ParameterUtil.dateTimeCheck(request.getParameter("startDate"), "00:00:00");
        Date endDate = ParameterUtil.dateTimeCheck(request.getParameter("endDate"), "23:59:59");

        RechargeDTO rechargeDTO = new RechargeDTO();
        rechargeDTO.setMemberID(memberID);
        rechargeDTO.setStartDateTime(startDate);
        rechargeDTO.setEndDateTime(endDate);

        return recharge.getRechargePagination(offset, limit, rechargeDTO);
    }
}
