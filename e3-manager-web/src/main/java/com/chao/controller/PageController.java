/**
 * 
 */
package com.chao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chao.pojo.EasyUIResult;
import com.chao.service.IItemService;

/**  
* Title: PageController.java  
* @author ChaoSir 
* @date 2019年9月11日  
* @version 1.0  
*/
@Controller
@Scope("prototype")
public class PageController {
	
	@RequestMapping("/")
	public String showIndex() {
		return "index";
	}
	
	@RequestMapping("/{page}")
	public String toPage(@PathVariable String page) {
		return page;
	}
	
}
