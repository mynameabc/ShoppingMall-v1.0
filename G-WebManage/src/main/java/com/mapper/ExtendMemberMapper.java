package com.mapper;

import model.dto.manage.MemberListDTO;
import model.entity.extend.MemberExtend;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExtendMemberMapper {

    List<MemberExtend> getMemberList(@Param("memberListDTO") MemberListDTO memberListDTO);
}
