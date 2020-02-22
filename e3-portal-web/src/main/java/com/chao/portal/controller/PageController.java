/**
 * 
 */
package com.chao.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chao.content.service.IContentService;
import com.chao.pojo.TbContent;

/**  
* Title: PageController.java  
* @author ChaoSir 
* @date 2019年9月24日  
* @version 1.0  
*/
@Controller
@Scope("prototype")
public class PageController {

	@Value("${CONTENT_CATEGORY_ID}")
	private long CONTENT_CATEGORY_ID;
	
	
	@Autowired
	private IContentService  contentService;
	
	@RequestMapping("/index.html")
	public String showIndex(ModelMap modelMap) {
		List<TbContent> contentByCategoryId = contentService.getContentByCategoryId(CONTENT_CATEGORY_ID);
		modelMap.put("ad1List", contentByCategoryId);
		return "index";
	}
	
}
