/**
 * 
 */
package com.chao.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chao.user.service.ICheckUserService;
import com.chao.utils.E3Result;

/**  
* Title: CheckUserController.java  
* @author ChaoSir 
* @date 2019年10月12日  
* @version 1.0  
*/
@Controller
@Scope("prototype")
public class CheckUserController {

	@Autowired
	private ICheckUserService  checkUserService;
	
	@RequestMapping("/user/checkData/{param}/{type}")
	@ResponseBody
	public E3Result checkData(@PathVariable String param, @PathVariable Integer type) {
		
		System.out.println(param +":"+ type);
		E3Result e3Result = checkUserService.checkUser(param, type);
		return e3Result;
		
	}
	
}
