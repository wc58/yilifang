/**
 * 
 */
package com.chao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chao.pojo.EasyUIResult;
import com.chao.pojo.TbItem;
import com.chao.service.IItemService;
import com.chao.utils.E3Result;

/**
 * Title: ItemController.java
 * 
 * @author ChaoSir
 * @date 2019年9月22日
 * @version 1.0
 */
@Controller
@Scope("prototype")
public class ItemController {

	@Autowired
	private IItemService iItemService;

	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIResult getItemList(Integer page, Integer rows) {

		EasyUIResult pageList = iItemService.getPageList(page, rows);

		return pageList;
	}

	@RequestMapping("/item/save")
	@ResponseBody
	public E3Result addItem(TbItem item, String desc) {
		try {
			iItemService.addItem(item, desc);
		} catch (Exception e) {
			return new E3Result(500, null, null);
		}
		return E3Result.ok();
	}

}
