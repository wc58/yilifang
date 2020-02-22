/**
 * 
 */
package com.chao.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chao.user.service.IUserService;
import com.chao.utils.CookieUtils;
import com.chao.utils.E3Result;

/**  
* Title: LoginUserController.java  
* @author ChaoSir 
* @date 2019年10月12日  
* @version 1.0  
*/
@Controller
@Scope("prototype")
public class LoginUserController {

	@Autowired
	private IUserService iUserService;
	
	/*
	 * 登录页面跳转
	 */
	@RequestMapping("/user/login")
	public String login() {
		return "login";
	}
	
	
	/*
	 * 登录
	 */
	@RequestMapping(value = "/user/loginUser",method = RequestMethod.POST)
	@ResponseBody
	public E3Result loginUser(String username,String password,HttpServletRequest request,HttpServletResponse response) {
		
		//接收参数
		//根据参数查找值
		E3Result result = iUserService.loginUser(username, password);
		//取coken
		String coken = (String) result.getData();
		//写入cookie
		CookieUtils.setCookie(request, response, "COOKIE_COKEN_KEY", coken);
		//响应数据
		return result;
	}
	
}
