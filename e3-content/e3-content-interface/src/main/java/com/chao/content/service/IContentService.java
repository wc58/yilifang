/**
 * 
 */
package com.chao.content.service;

import java.util.List;

import com.chao.pojo.TbContent;

/**  
* Title: IContentService.java  
* @author ChaoSir 
* @date 2019年9月24日  
* @version 1.0  
*/
public interface IContentService {

	List<TbContent> getContentByCategoryId(Long categoryId);
	
	void addContent(TbContent content);
	
}
