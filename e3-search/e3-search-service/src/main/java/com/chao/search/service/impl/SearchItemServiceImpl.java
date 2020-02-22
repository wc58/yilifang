/**
 * 
 */
package com.chao.search.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chao.pojo.SearchItem;
import com.chao.search.mapper.SearchMapper;
import com.chao.search.service.ISearchItemService;
import com.chao.utils.E3Result;

/**
 * Title: SearchItemServiceImpl.java
 * 
 * @author ChaoSir
 * @date 2019年10月1日
 * @version 1.0
 */
@Service
public class SearchItemServiceImpl implements ISearchItemService {

	@Autowired
	private SearchMapper mapper;

	@Autowired
	private SolrServer server;

	@Override
	public void importItems() {
		try {
			List<SearchItem> searchItem = mapper.SearchItem();
			for (SearchItem searchItem2 : searchItem) {
				SolrInputDocument solrDocument = new SolrInputDocument();
				solrDocument.addField("id", searchItem2.getId());
				solrDocument.addField("item_title", searchItem2.getTitle());
				solrDocument.addField("item_sell_point", searchItem2.getSell_point());
				solrDocument.addField("item_price", searchItem2.getPrice());
				solrDocument.addField("item_image", searchItem2.getImage());
				solrDocument.addField("item_category_name", searchItem2.getCategory_name());
				server.add(solrDocument);
			}
			server.commit();
			
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
		}
	}

}
