/**
 * 
 */
package com.chao.search.listener;

import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import com.chao.pojo.SearchItem;
import com.chao.search.mapper.SearchMapper;

/**
 * Title: ItemMessageListener.java
 * 
 * @author ChaoSir
 * @date 2019年10月9日
 * @version 1.0
 */
public class ItemMessageListener implements MessageListener {

	// 注入mapper
	@Autowired
	private SearchMapper mapper;

	@Autowired
	private SolrServer server;

	@Override
	public void onMessage(Message message) {
		try {
			// 获取activeMQ传入信息
			TextMessage textMessage = (TextMessage) message;
			String text = textMessage.getText();
			// 转型
			Long itemId = new Long(text);
			// 根据ID查询
			SearchItem item = mapper.SearchItemById(itemId);

			// 创建文档对象
			SolrInputDocument document = new SolrInputDocument();
			// 创建域
			document.addField("id", item.getId());
			document.addField("item_title", item.getTitle());
			document.addField("item_sell_point", item.getSell_point());
			document.addField("item_price", item.getPrice());
			document.addField("item_image", item.getImage());
			document.addField("item_category_name", item.getCategory_name());
			// 写入索引库
			server.add(document);
			// 提交
			server.commit();

		} catch (JMSException | SolrServerException | IOException e) {
			e.printStackTrace();
		}

	}

}
