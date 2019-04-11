package com.controller.member;

import com.alibaba.fastjson.JSON;
import com.service.member.IMemberManage;
import communal.util.RegexUtil;
import model.dto.manage.MemberListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "member")
public class MemberController {

    @Autowired
    private IMemberManage memberManage;

    /**
     * 会员管理页面
     * @return
     */
    @GetMapping(value = "member-list")
    public String list() {
        return "modules/member/member-list";
    }

    /**
     * 会员详细信息页面
     * @return
     */
    @GetMapping(value = "member-detailed")
    public String memberDetailed() {
        return "modules/member/member-detailed";
    }

    /**
     * 会员修改密码页面
     * @return
     */
    @GetMapping(value = "change-password")
    public String changePassword() {
        return "modules/member/change-password";
    }

    /**
     * 修改会员密码
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping(value = "updatePassword")
    public String updatePassword(HttpServletRequest request) {

        String memberID = request.getParameter("memberID");
        String password = request.getParameter("pws");
        String confirm_pws = request.getParameter("confirm_pws");

        return JSON.toJSONString(memberManage.updateMemberPassword(
                Long.valueOf(memberID).intValue(),
                password,
                confirm_pws
        ));
    }

    /**
     * 启用用户
     * @param request
     * @return
     */
    @PostMapping(value = "member_start")
    @ResponseBody
    public String member_start(HttpServletRequest request) {

        String memberID = request.getParameter("memberID"); if (null == memberID) return null;
        return JSON.toJSONString(memberManage.memberStart(Long.valueOf(memberID)));
    }

    /**
     * 停用用户
     * @param request
     * @return
     */
    @PostMapping(value = "member_stop")
    @ResponseBody
    public String member_stop(HttpServletRequest request) {

        String memberID = request.getParameter("memberID"); if (null == memberID) return null;
        return JSON.toJSONString(memberManage.memberStop(Long.valueOf(memberID)));
    }

    @PostMapping(value = "getMemberList")
    @ResponseBody
    public String getMemberList(HttpServletRequest request) {

        Integer limit = Integer.parseInt(request.getParameter("limit"));
        Integer offset = Integer.parseInt(request.getParameter("offset"));
        String account = request.getParameter("account");
        String parentID = request.getParameter("parentID");
        String login_lock = request.getParameter("login_lock");
        String memberRanksZTreeID = request.getParameter("memberRanksZTreeID");

        MemberListDTO memberListDTO = new MemberListDTO();
        if (null != account && account.length() >= 1) {

            if (RegexUtil.mobile(account)) {
                memberListDTO.setMobile(account);
            } else if (RegexUtil.email(account)) {
                memberListDTO.setEmail(account);
            } else {
                memberListDTO.setName(account);
            }
        }
        if (null != parentID && parentID.length() >= 0 && Integer.valueOf(parentID).intValue() != 0) {memberListDTO.setParentID(Integer.parseInt(parentID));}
        if (null != login_lock && login_lock.length() >= 1 && Integer.valueOf(login_lock).intValue() != 0) {memberListDTO.setLogin_lock(Integer.parseInt(login_lock));}
        if (null != memberRanksZTreeID && memberRanksZTreeID.length() >= 1 && Integer.valueOf(memberRanksZTreeID).intValue() != 0) {memberListDTO.setMemberGradeID(Integer.parseInt(memberRanksZTreeID));}

        return memberManage.getMemberList(offset,limit, memberListDTO);
    }
}
