/**
 * 
 */
package com.chao.search.service;

import com.chao.pojo.SearchResult;

/**  
* Title: ISearchService.java  
* @author ChaoSir 
* @date 2019年10月2日  
* @version 1.0  
*/
public interface ISearchService {

	SearchResult searchResult(String keyword, int page, int rows);
	
}
