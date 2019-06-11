package com.service.member;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mapper.ExtendMemberMapper;
import com.mapper.table.MemberMapper;
import com.service.member.detailed.RechargeServiceImpl;
import communal.Result;
import communal.util.LogUtil;
import model.dto.manage.MemberListDTO;
import model.entity.Member;
import model.entity.extend.MemberExtend;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberManage implements IMemberManage {

    private static Logger logger = LogManager.getLogger(MemberManage.class);

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private ExtendMemberMapper extendMemberMapper;

    /**
     * 启用用户
     * @param memberID
     * @return
     */
    public Result memberStart(Long memberID) {
        Member member = null;
        try{
            member = memberMapper.selectByPrimaryKey(memberID);
        } catch (Exception e) {
            LogUtil.error(logger);
            return new Result(false, "启用失败:数据库异常!");
        }

        if (null == member) {
            return new Result(false, "该会员不存在!");
        }

        member.setLogin_lock(true);
        int count = 0;
        try {
            count = memberMapper.updateByPrimaryKeySelective(member);
        } catch (Exception e) {
            LogUtil.error(logger);
            return new Result(false, "启用失败:数据库异常!");
        }

        return (count >= 1) ?
                (new Result(true, "修改成功!")) :
                (new Result(false, "修改失败!"));
    }

    /**
     * 停用用户
     * @param memberID
     * @return
     */
    public Result memberStop(Long memberID) {
        Member member = null;
        try{
            member = memberMapper.selectByPrimaryKey(memberID);
        } catch (Exception e) {
            LogUtil.error(logger);
            return new Result(false, "停用失败:数据库异常!");
        }

        if (null == member) {
            return new Result(false, "该会员不存在!");
        }

        member.setLogin_lock(false);
        int count = 0;
        try {
            count = memberMapper.updateByPrimaryKeySelective(member);
        } catch (Exception e) {
            LogUtil.error(logger);
            return new Result(false, "停用失败:数据库异常!");
        }

        return (count >= 1) ?
                (new Result(true, "修改成功!")) :
                (new Result(false, "修改失败!"));
    }

    /**
     * 修改会员密码
     * @param memberID
     * @param pws
     * @param confirm_pws
     * @return
     */
    public Result updateMemberPassword(long memberID, String pws, String confirm_pws) {

        Member member = null;
        try{
            member = memberMapper.selectByPrimaryKey(memberID);
        } catch (Exception e) {
            LogUtil.error(logger);
            return new Result(false, "修改失败:数据库异常!");
        }

        if (null == member) {
            return new Result(false, "该会员不存在!");
        }

        if (!pws.trim().equals(confirm_pws.trim())) {
            return new Result(false, "两次密码不一致!");
        }

        member.setPws(getPasswordEncoder().encode(pws));
        int count = 0;
        try {
            count = memberMapper.updateByPrimaryKeySelective(member);
        } catch (Exception e) {
            LogUtil.error(logger);
            return new Result(false, "修改失败:数据库异常!");
        }

        return (count >= 1) ?
                (new Result(true, "修改成功!")) :
                (new Result(false, "修改失败!"));
    }

    /**
     * 分页查询
     * @param offset
     * @param limit
     * @param memberListDTO
     * @return
     */
    public String getMemberList(Integer offset, Integer limit, MemberListDTO memberListDTO) {

        PageHelper.offsetPage(offset, limit);
        Page<MemberExtend> memberList =
                (Page<MemberExtend>)extendMemberMapper.getMemberList(memberListDTO);

        JSONObject result = new JSONObject();
        result.put("rows", memberList.getResult());
        result.put("total", memberList.getTotal());
        return result.toJSONString();
    }

    private PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
