/**
 * 
 */
package com.chao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chao.content.service.IContentCategoryService;
import com.chao.pojo.EasyUITreeNode;

/**  
* Title: ContentCategoryController.java  
* @author ChaoSir 
* @date 2019年9月24日  
* @version 1.0  
*/
@Controller
@Scope("prototype")
public class ContentCategoryController {

	@Autowired
	private IContentCategoryService  categoryService;
	
	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<EasyUITreeNode> getContentCategoryList(@RequestParam(value = "id" ,defaultValue = "0") Long parentId) {
		
		List<EasyUITreeNode> contentCategoryList = categoryService.getContentCategoryList(parentId);
		return contentCategoryList;
	}
	
}
