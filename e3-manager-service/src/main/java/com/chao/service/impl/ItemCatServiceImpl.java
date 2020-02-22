/**
 * 
 */
package com.chao.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chao.mapper.TbItemCatMapper;
import com.chao.pojo.EasyUITreeNode;
import com.chao.pojo.TbItemCat;
import com.chao.pojo.TbItemCatExample;
import com.chao.pojo.TbItemCatExample.Criteria;
import com.chao.service.IItemCatService;

/**  
* Title: ItemCatServiceImpl.java  
* @author ChaoSir 
* @date 2019年9月18日  
* @version 1.0  
*/
@Service
public class ItemCatServiceImpl implements IItemCatService {

	@Autowired
	private TbItemCatMapper tbItemCatMapper;
	
	public List<EasyUITreeNode> getCatList(Long parentId) {
		
		TbItemCatExample tbItemCatExample = new TbItemCatExample();
		Criteria createCriteria = tbItemCatExample.createCriteria();
		createCriteria.andParentIdEqualTo(parentId);
		List<TbItemCat> selectByExample = tbItemCatMapper.selectByExample(tbItemCatExample);
		List<EasyUITreeNode> list = new ArrayList<EasyUITreeNode>();
		for (TbItemCat itemCat : selectByExample) {
			EasyUITreeNode easyUITreeNode = new EasyUITreeNode();
			easyUITreeNode.setId(itemCat.getId());
			easyUITreeNode.setText(itemCat.getName());
			easyUITreeNode.setState(itemCat.getIsParent()?"closed":"open");
			list.add(easyUITreeNode);
		}
		return list;
	}

}
