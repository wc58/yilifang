/**
 * 
 */
package com.chao.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.chao.jedis.JedisClient;
import com.chao.mapper.TbItemDescMapper;
import com.chao.mapper.TbItemMapper;
import com.chao.pojo.EasyUIResult;
import com.chao.pojo.TbItem;
import com.chao.pojo.TbItemDesc;
import com.chao.pojo.TbItemExample;
import com.chao.service.IItemService;
import com.chao.utils.IDUtils;
import com.chao.utils.JsonUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**  
* Title: ItemServiceImpl.java  
* @author ChaoSir 
* @date 2019年9月8日  
* @version 1.0  
*/
@Service
public class ItemServiceImpl implements IItemService {

	@Autowired
	private TbItemMapper itemMapper;
	
	@Autowired
	private TbItemDescMapper descMapper;
	
	//注入activeMQ
	@Autowired
	private JmsTemplate  jmsTemplate;
	
	//注入缓存
	@Autowired
	private JedisClient client;
	
	@Resource
	private Destination  TopicItemId;
	
	public TbItem getItemById(long id) {
		
		try {
			//取值
			String string = client.get("ITEM_INFO_PRE" + id + ":BASE");
			//判断
			if (StringUtils.isNotBlank(string)) {
				//转型
				TbItem tbItem = JsonUtils.jsonToPojo(string, TbItem.class);
				System.out.println("item向缓存中读取数据……");
				return tbItem;
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		TbItem selectByPrimaryKey = itemMapper.selectByPrimaryKey(id);
		
		try {
			//写入数据
			client.set("ITEM_INFO_PRE"+ id +":BASE", JsonUtils.objectToJson(selectByPrimaryKey));
			//设置日期
			System.out.println("item向缓存中写入数据……");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return selectByPrimaryKey;
	}

	public EasyUIResult getPageList(Integer page, Integer rows) {
		
		PageHelper.startPage(page, rows);
		
		TbItemExample example = new TbItemExample();
		List<TbItem> selectByExample = itemMapper.selectByExample(example );
		PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(selectByExample);
		
		EasyUIResult easyUIResult = new EasyUIResult();
		easyUIResult.setTotal(pageInfo.getTotal());
		easyUIResult.setRows(selectByExample);
		
		return easyUIResult;
	}

	@Override
	public void addItem(TbItem item, String desc) {
		final long genItemId = IDUtils.genItemId();
		item.setId(genItemId);
		item.setStatus((byte) 1);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		itemMapper.insert(item);
		TbItemDesc itemDesc =new TbItemDesc();
		itemDesc.setItemId(genItemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(new Date());
		descMapper.insert(itemDesc);
		
		//activeMQ发送item的id
		jmsTemplate.send(TopicItemId,new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage();
				textMessage.setText(genItemId + "");
				return textMessage;
			}
		});
		
	}

}
