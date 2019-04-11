package com.service.member;

import communal.Result;
import model.dto.manage.MemberListDTO;

public interface IMemberManage {

    /**
     * 启用用户
     * @param memberID
     * @return
     */
    Result memberStart(Long memberID);

    /**
     * 停用用户
     * @param memberID
     * @return
     */
    Result memberStop(Long memberID);

    /**
     * 修改会员密码
     * @param memberID
     * @param pws
     * @param confirm_pws
     * @return
     */
    Result updateMemberPassword(long memberID, String pws, String confirm_pws);

    /**
     * 会员列表分页
     * @param offset
     * @param limit
     * @param memberListDTO
     * @return
     */
    String getMemberList(Integer offset, Integer limit, MemberListDTO memberListDTO);
}
