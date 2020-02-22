/**
 * 
 */
package com.chao.item.listener;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.chao.item.pojo.Item;
import com.chao.pojo.TbItem;
import com.chao.pojo.TbItemDesc;
import com.chao.service.IItemDescService;
import com.chao.service.IItemService;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Title: ItemMessageListener.java
 * 
 * @author ChaoSir
 * @date 2019年10月11日
 * @version 1.0
 */
public class ItemMessageListener implements MessageListener {

	@Autowired
	private IItemService   itemService;
	
	@Autowired
	private IItemDescService  descService;
	
	@Autowired
	private FreeMarkerConfigurer  configurer;
	
	@SuppressWarnings("resource")
	@Override
	public void onMessage(Message message) {
		try {
			// 获得信息
			TextMessage textMessage = (TextMessage) message;
			String text = textMessage.getText();
			//转型
			Long itemId = new Long(text);
			//根据id获得对象
			TbItem tbItem = itemService.getItemById(itemId);
			Item item = new Item(tbItem);
			TbItemDesc itemDesc = descService.geTbItemDesc(itemId);
			
			//获得配置器
			Configuration configuration = configurer.getConfiguration();
			//获得模板
			Template template = configuration.getTemplate("item.ftl");
			//注入属性
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("item", item);
			map.put("itemDesc", itemDesc);
			//文件输出地址
			FileWriter fileWriter = new FileWriter("E:\\temp\\out\\"+ text +".html");
			//使用proecss输出
			template.process(map, fileWriter);
			//关闭资源
			fileWriter.close();
			
			
		} catch (JMSException | IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
