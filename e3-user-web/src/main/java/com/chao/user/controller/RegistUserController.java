/**
 * 
 */
package com.chao.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chao.pojo.TbUser;
import com.chao.user.service.IUserService;
import com.chao.utils.E3Result;

/**  
* Title: RegistUserController.java  
* @author ChaoSir 
* @date 2019年10月12日  
* @version 1.0  
*/
@Controller
@Scope("prototype")
public class RegistUserController {

	@Autowired
	private IUserService iUserService;
	
	/*
	 * 注册用户页面跳转
	 */
	
	@RequestMapping("/user/register")
	public String regisUser() {
		return "register";
	}
	
	/*
	 * 创建用户
	 */
	@RequestMapping(value = "/user/createUser", method = RequestMethod.POST)
	@ResponseBody
	public E3Result createUser(TbUser user) {
		E3Result result = iUserService.createUser(user);
		return result;
	}

}
