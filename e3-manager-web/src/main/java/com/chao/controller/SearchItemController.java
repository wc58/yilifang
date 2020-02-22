/**
 * 
 */
package com.chao.controller;

import javax.jws.soap.SOAPBinding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.ParameterMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chao.search.service.ISearchItemService;
import com.chao.utils.E3Result;

/**
 * Title: SearchItemController.java
 * 
 * @author ChaoSir
 * @date 2019年10月2日
 * @version 1.0
 */
@Controller
@Scope("prototype")
public class SearchItemController {

	@Autowired
	private ISearchItemService iSearchItemService;

	@RequestMapping("/index/item/import")
	@ResponseBody
	public E3Result importItem() {
		try {
			iSearchItemService.importItems();
		} catch (Exception e) {
			e.printStackTrace();
			return E3Result.build(500, "插入失败！");
		}
		return E3Result.ok();
	}

}
