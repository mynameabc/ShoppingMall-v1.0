package com.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
* @ClassName: LoginController 
* @Description: 登录控制的controller
* @author fuweilian
* @date 2018-5-12 下午01:15:46
 */
@RequestMapping
@Controller
public class LoginController {

	private Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String getLogin(){
		logger.info("进入login页面");
		return "login";
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String doLogin(HttpServletRequest req, Map<String, Object> model){
		logger.info("进入登录处理");
		String exceptionClassName = (String) req.getAttribute("shiroLoginFailure");
		logger.info("exceptionClassName:"+exceptionClassName);
		String error = null;
		if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
			error = "用户名/密码错误";
		} else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
			error = "用户名/密码错误";
		}else if(LockedAccountException.class.getName().equals(exceptionClassName)){ 
			error = "用户已锁定或已删除";
		}else if (exceptionClassName != null) {
			error = "其他错误：" + exceptionClassName;
		}
		if(SecurityUtils.getSubject().isAuthenticated()){//没有错误，但是已经登录了，就直接跳转到welcom页面
			model.put("name", req.getParameter("userName"));
			return "index";
		}else{//有错误的
			model.put("error", error);
			return "login";
		}
	}
	@RequestMapping("/index")
	public String index(){
		return "index";
	}
}
