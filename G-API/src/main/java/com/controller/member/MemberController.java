package com.controller.member;

import com.alibaba.fastjson.JSON;
import communal.Result;
import communal.util.param.ParameterUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import model.dto.LoginDTO;
import module.noncore.member.ILogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Api(tags="会员模块")
@RestController
@RequestMapping(value="/Member")
@EnableAutoConfiguration
public class MemberController {

    @Autowired
    @Qualifier("loginDecoration")
    private ILogin loginImpl;

    @ApiOperation(value="登陆", notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account", required=true,   paramType="form",  dataType="String", value="账号(手机号码)"),
            @ApiImplicitParam(name="password",required=true,   paramType="form",  dataType="String", value="密码")
    })
    @PostMapping(value="login", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String login(String account, String password){

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setAccount(ParameterUtil.stringCheck(account, ""));
        loginDTO.setPassword(ParameterUtil.stringCheck(password, ""));
        Result result = loginDTO.checkDTO();
        if (!result.isSuccess()) {return JSON.toJSONString(result);}
        return JSON.toJSONString(loginImpl.login(loginDTO));
    }

    @ApiOperation(value="注册", notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(name="account", required=true,   paramType="form",  dataType="String", value="账号(手机号码/邮箱/呢称)"),
            @ApiImplicitParam(name="password",required=true,   paramType="form",  dataType="String", value="密码")
    })
    @PostMapping(value="register", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result register(String account, String password){
        return new Result(true, "注册成功!");
    }

    @ApiOperation(value="个人信息", notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(name="memberID",required=true,   paramType="path",  dataType="Long", value="会员ID")
    })
    @GetMapping(value="getMemberInfo/{memberID}", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result getMemberInfo(@PathVariable(name="memberID")Long memberID){
        return new Result(true, "返回成功!", null);
    }
}
