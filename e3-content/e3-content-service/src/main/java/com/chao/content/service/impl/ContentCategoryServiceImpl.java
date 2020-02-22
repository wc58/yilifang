/**
 * 
 */
package com.chao.content.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chao.content.service.IContentCategoryService;
import com.chao.mapper.TbContentCategoryMapper;
import com.chao.pojo.EasyUITreeNode;
import com.chao.pojo.TbContentCategory;
import com.chao.pojo.TbContentCategoryExample;
import com.chao.pojo.TbContentCategoryExample.Criteria;

/**
 * Title: ContentCatServiceImpl.java
 * 
 * @author ChaoSir
 * @date 2019年9月24日
 * @version 1.0
 * @param <E>
 */
@Service
public class ContentCategoryServiceImpl<E> implements IContentCategoryService {

	@Autowired
	private TbContentCategoryMapper categoryMapper;

	@Override
	public List<EasyUITreeNode> getContentCategoryList(Long parentId) {

		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> selectByExample = categoryMapper.selectByExample(example);

		ArrayList<EasyUITreeNode> arrayList = new ArrayList<EasyUITreeNode>();
		for (TbContentCategory tbContentCategory : selectByExample) {
			EasyUITreeNode easyUITreeNode = new EasyUITreeNode();
			easyUITreeNode.setId(tbContentCategory.getId());
			easyUITreeNode.setText(tbContentCategory.getName());
			easyUITreeNode.setState(tbContentCategory.getIsParent()?"closed":"open");
			arrayList.add(easyUITreeNode);
		}
		
		return arrayList;
	}

}
