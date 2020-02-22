/**
 * 
 */
package com.chao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.support.Parameter;
import com.chao.pojo.EasyUITreeNode;
import com.chao.service.IItemCatService;

/**  
* Title: ItemCatConterller.java  
* @author ChaoSir 
* @date 2019年9月18日  
* @version 1.0  
*/
@Controller
@Scope("prototype")
public class ItemCatController {

	@Autowired
	private IItemCatService iItemCatService;
	
	@RequestMapping("/item/cat/list")
	@ResponseBody
	public List<EasyUITreeNode> getCatList(@RequestParam(value="id",defaultValue="0")Long parentId){
		
		System.out.println(parentId);
		List<EasyUITreeNode> catList = iItemCatService.getCatList(parentId);
		
		return catList;
	}
	
}
