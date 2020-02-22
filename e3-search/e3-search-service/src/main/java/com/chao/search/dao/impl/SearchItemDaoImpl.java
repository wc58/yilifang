/**
 * 
 */
package com.chao.search.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chao.pojo.SearchItem;
import com.chao.pojo.SearchResult;
import com.chao.search.dao.ISearchItemDao;

/**
 * Title: SearchItemDaoImpl.java
 * 
 * @author ChaoSir
 * @date 2019年10月2日
 * @version 1.0
 */
@Repository
public class SearchItemDaoImpl  implements ISearchItemDao {

	@Autowired
	private SolrServer server;

	public SearchResult addSearchItem(SolrQuery query) throws Exception {

		QueryResponse queryResponse = server.query(query);
		Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
		SolrDocumentList results = queryResponse.getResults();

		List<SearchItem> itemsList = new ArrayList<SearchItem>();
		for (SolrDocument solrDocument : results) {
			SearchItem searchItem = new SearchItem();
			searchItem.setId((String) solrDocument.get("id"));
			searchItem.setCategory_name((String) solrDocument.get("item_category_name"));
			searchItem.setImage((String) solrDocument.get("item_image"));
			searchItem.setPrice((long) solrDocument.get("item_price"));
			searchItem.setSell_point((String) solrDocument.get("item_sell_point"));

			 List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			//高亮
			String title = null;
			if (list != null || list.size() != 0) {
				title = list.get(0);
			} else {
				title = (String) solrDocument.get("item_title");
			}
			searchItem.setTitle(title);

			itemsList.add(searchItem);
		}
		long numFound = results.getNumFound();

		SearchResult searchResult = new SearchResult();
		searchResult.setItemList(itemsList);
		searchResult.setRecordCount(numFound);

		return searchResult;
	}

}
