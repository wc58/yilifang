/**
 * 
 */
package com.chao.solrj;

import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

/**  
* Title: SolrCloudJ.java  
* @author ChaoSir 
* @date 2019年10月6日  
* @version 1.0  
*/
public class SolrCloudJ {

	@Test
	public void add() throws Exception {
		
		CloudSolrServer cloudSolrServer = new CloudSolrServer("192.168.25.130:2181,192.168.25.130:2182,192.168.25.130:2183");
		cloudSolrServer.setDefaultCollection("collection1");
		SolrInputDocument solrInputDocument = new SolrInputDocument();
		solrInputDocument.addField("id", "1");
		solrInputDocument.addField("item_title", "超哥哥");
		solrInputDocument.addField("item_price", "17");
		cloudSolrServer.add(solrInputDocument);
		cloudSolrServer.commit();
		
	}
	
}
