/**
 * 
 */
package com.chao.search.dao;

import org.apache.solr.client.solrj.SolrQuery;

import com.chao.pojo.SearchResult;

/**  
* Title: ISearchItemDao.java  
* @author ChaoSir 
* @date 2019年10月2日  
* @version 1.0  
*/
public interface ISearchItemDao {

	SearchResult addSearchItem(SolrQuery query) throws Exception;
	
}
