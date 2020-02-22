/**
 * 
 */
package com.chao.service;

import java.util.List;

import com.chao.pojo.EasyUITreeNode;

/**  
* Title: IItemCatService.java  
* @author ChaoSir 
* @date 2019年9月18日  
* @version 1.0  
*/
public interface IItemCatService {

	public List<EasyUITreeNode> getCatList(Long parentId);
	
}
