package com.mapper.table;

import com.MyMapper;
import model.dto.manage.MemberListDTO;
import model.entity.Member;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MemberMapper extends MyMapper<Member> {

    List<Member> getMemberList(@Param("memberListDTO") MemberListDTO memberListDTO);

    Long countAll();
}
