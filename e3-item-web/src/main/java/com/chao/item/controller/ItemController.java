/**
 * 
 */
package com.chao.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chao.item.pojo.Item;
import com.chao.pojo.TbItemDesc;
import com.chao.service.IItemDescService;
import com.chao.service.IItemService;

/**  
* Title: ItemController.java  
* @author ChaoSir 
* @date 2019年10月9日  
* @version 1.0  
*/
@Controller
@Scope("prototype")
public class ItemController {

	@Autowired
	private IItemService iItemService;
	
	@Autowired
	private IItemDescService iItemDesc;
	
	@RequestMapping("/item/{itemId}")
	public String showItemInfo(@PathVariable Long itemId,Model model) {
		
		
		//查询商品描述
		TbItemDesc itemDesc = iItemDesc.geTbItemDesc(itemId);
		//查询,封装商品信息
		Item item = new Item(iItemService.getItemById(itemId));
		//添加商品信息和商品描述
		model.addAttribute("item", item);
		model.addAttribute("itemDesc", itemDesc);
		
		
		return "item";
	}
	
	
}
