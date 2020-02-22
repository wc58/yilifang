/**
 * 
 */
package com.chao.solrj;

import static org.junit.Assert.*;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.SolrParams;
import org.junit.Test;

/**  
* Title: SolrjTest.java  
* @author ChaoSir 
* @date 2019年10月1日  
* @version 1.0  
*/
public class SolrjTest {

	@Test
	public void add() throws Exception {
		
		SolrServer server = new HttpSolrServer("http://192.168.25.130:8080/solr/");
		SolrInputDocument solrInputDocument = new SolrInputDocument();
		solrInputDocument.addField("id", "1");
		solrInputDocument.addField("item_title", "超哥哥");
		solrInputDocument.addField("item_price", "17");
		server.add(solrInputDocument);
		server.commit();
	}
	
	@Test
	public void delete() throws Exception {
		
		SolrServer server = new HttpSolrServer("http://192.168.25.130:8080/solr/");
		server.deleteById("1"); 
		server.commit();
		
	}
	
	@Test
	public void query() throws Exception {
		
		SolrServer server = new HttpSolrServer("http://192.168.25.130:8080/solr/");
		SolrQuery query = new SolrQuery();
		query.setQuery("*:*");
		
		query.setHighlight(true);
		query.addHighlightField("tiem_title");
		query.setHighlightSimplePre("<h1>");
		query.setHighlightSimplePost("</h1>");
		
		QueryResponse queryResponse = server.query(query);
		SolrDocumentList results = queryResponse.getResults();
		System.out.println("总记录数：" + results.getNumFound());
		for (SolrDocument solrDocument : results) {
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("item_title"));
			System.out.println(solrDocument.get("item_price"));
		}
		
	}
	
}
