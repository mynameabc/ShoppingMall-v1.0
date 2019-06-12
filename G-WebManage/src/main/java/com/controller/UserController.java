package com.controller;

import java.util.ArrayList;
import java.util.List;
 
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
 
/**
* @ClassName: UserController 
* @Description: 用户处理Controller
* @author fuweilian
* @date 2018-5-12 下午03:11:06
 */
@Controller
@RequestMapping("/user")
public class UserController {

	Logger logger = LoggerFactory.getLogger(UserController.class);

	@RequiresPermissions("user:list")//这个是配置是否有该权限的，如果是按上面的写法，这个是有权限的
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String getList(){
		logger.info("进入用户列表");
		return "user/list";
	}
	@RequiresPermissions(value={"user:add"})//这个是没有权限的
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String getAdd(){
		logger.info("进入新增用户界面");
		return "user/add";
	}
	
}
