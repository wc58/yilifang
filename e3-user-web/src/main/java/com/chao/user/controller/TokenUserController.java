/**
 * 
 */
package com.chao.user.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chao.pojo.TbUser;
import com.chao.pojo.TbUserExample;
import com.chao.user.service.IUserService;
import com.chao.utils.CookieUtils;
import com.chao.utils.E3Result;
import com.chao.utils.JsonUtils;

/**
 * Title: UserController.java
 * 
 * @author ChaoSir
 * @date 2019年10月12日
 * @version 1.0
 */
@Controller
@Scope("prototype")
public class TokenUserController {

	@Autowired
	private IUserService iUserService;

	/*
	 * 根据coken获得对象
	 */
	@RequestMapping(value = "/user/getUser/{token}",produces = "application/json;charset=utf-8")
	@ResponseBody
	public String getUserByCoken(@PathVariable String token, String callback) {

		E3Result result = iUserService.getUserByCoken(token);
		// 判断是否是跨域请求
		if (StringUtils.isNotBlank(callback)) {
			return callback + "(" + JsonUtils.objectToJson(result) + ");";
		}
		return JsonUtils.objectToJson(result);
	}

	/*
	 * 根据coken退出登录
	 */
	@RequestMapping(value = "/user/logout")
	public String delUser(String COOKIE_COKEN_KEY,HttpServletResponse httpServletResponse) throws IOException {

		E3Result result = iUserService.logoout(COOKIE_COKEN_KEY);
		
		return "redirect:http://localhost:8280/index.html";
	}

}
