/**
 * 
 */
package com.chao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chao.mapper.TbItemMapper;
import com.chao.pojo.TbItem;
import com.chao.service.IItemService;

/**  
* Title: ItemServiceImpl.java  
* @author ChaoSir 
* @date 2019年9月8日  
* @version 1.0  
*/
@Service
public class ItemServiceImpl implements IItemService {

	@Autowired
	private TbItemMapper itemMapper;
	
	public TbItem getItemById(long id) {
		TbItem selectByPrimaryKey = itemMapper.selectByPrimaryKey(id);
		return selectByPrimaryKey;
	}

}
