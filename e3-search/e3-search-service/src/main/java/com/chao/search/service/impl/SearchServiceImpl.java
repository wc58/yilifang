/**
 * 
 */
package com.chao.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chao.pojo.SearchResult;
import com.chao.search.dao.ISearchItemDao;
import com.chao.search.dao.impl.SearchItemDaoImpl;
import com.chao.search.service.ISearchService;

/**
 * Title: SearchServiceImpl.java
 * 
 * @author ChaoSir
 * @date 2019年10月2日
 * @version 1.0
 */
@Service
public class SearchServiceImpl implements ISearchService {

	@Autowired
	private ISearchItemDao searchItemDaoImpl;

	@Override
	public SearchResult searchResult(String keyword, int page, int rows) {

		if (page <= 0)
			page = 1;
		if (rows <= 0)
			rows = 10;

		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery(keyword);
		solrQuery.set("df", "item_title");

		solrQuery.setStart((page - 1) * rows);
		solrQuery.setRows(rows);

		solrQuery.setHighlight(true);
		solrQuery.addHighlightField("item_title");
		solrQuery.setHighlightSimplePre("<font color='red'>");
		solrQuery.setHighlightSimplePost("</font>");
		
		SearchResult searchResult = null;
		try {
			searchResult = searchItemDaoImpl.addSearchItem(solrQuery);
		} catch (Exception e) {
			e.printStackTrace();
		}

		long recordCount = searchResult.getRecordCount();
		int totalPage = (int) (recordCount / rows);
		if(recordCount % rows != 0) totalPage ++;
		searchResult.setTotalPages(totalPage);
		
		return searchResult;
	}

}
