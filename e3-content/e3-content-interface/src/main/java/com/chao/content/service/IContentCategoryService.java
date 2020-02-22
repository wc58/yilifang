/**
 * 
 */
package com.chao.content.service;

import java.util.List;

import com.chao.pojo.EasyUITreeNode;

/**  
* Title: IContentCatService.java  
* @author ChaoSir 
* @date 2019年9月24日  
* @version 1.0  
*/
public interface IContentCategoryService {

	List<EasyUITreeNode> getContentCategoryList(Long parentId);
	
}
