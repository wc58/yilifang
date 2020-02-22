/**
 * 
 */
package com.chao.search.mapper;

import java.io.Serializable;
import java.util.List;

import com.chao.pojo.SearchItem;

/**  
* Title: SearchMapper.java  
* @author ChaoSir 
* @date 2019年10月1日  
* @version 1.0  
*/
public interface SearchMapper {

	List<SearchItem> SearchItem();
	SearchItem SearchItemById(Long id);
	
}
