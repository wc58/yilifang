/**
 * 
 */
package com.chao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chao.content.service.IContentService;
import com.chao.pojo.TbContent;
import com.chao.utils.E3Result;

/**
 * Title: ContentController.java
 * 
 * @author ChaoSir
 * @date 2019年9月24日
 * @version 1.0
 */
@Controller
@Scope("prototype")
public class ContentController {

	@Autowired
	private IContentService contentService;

	@RequestMapping("/content/save")
	@ResponseBody
	public E3Result addContent(TbContent content) {
		try {
			contentService.addContent(content);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("商品添加失败！！！");
		}
		return E3Result.ok();
	}

}
