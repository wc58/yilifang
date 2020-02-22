/**
 * 
 */
package com.chao.content.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.chao.content.service.IContentService;
import com.chao.jedis.JedisClient;
import com.chao.mapper.TbContentMapper;
import com.chao.pojo.TbContent;
import com.chao.pojo.TbContentExample;
import com.chao.pojo.TbContentExample.Criteria;
import com.chao.utils.JsonUtils;
import com.mysql.fabric.xmlrpc.base.Data;

/**
 * Title: ContentServiceImp.java
 * 
 * @author ChaoSir
 * @date 2019年9月24日
 * @version 1.0
 */
@Service
public class ContentServiceImpl implements IContentService {

	@Autowired
	private TbContentMapper contentMapper;
	@Autowired
	private JedisClient  client;
	@Value("${CONTENT_LIST}")
	private String CONTENT_LIST;
	
	@Override
	public List<TbContent> getContentByCategoryId(Long categoryId) {
		try {
			String hget = client.hget(CONTENT_LIST, categoryId+"");
			if (StringUtils.isNotEmpty(hget)) {
				List<TbContent> jsonToList = JsonUtils.jsonToList(hget, TbContent.class);
				System.out.println("取缓存中的值…………");
				return jsonToList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		TbContentExample tbContentExample = new TbContentExample();
		Criteria createCriteria = tbContentExample.createCriteria();
		createCriteria.andCategoryIdEqualTo(categoryId);
		List<TbContent> list = contentMapper.selectByExampleWithBLOBs(tbContentExample);
		
		try {
			client.hset(CONTENT_LIST, categoryId + "", JsonUtils.objectToJson(list));
			System.out.println("向缓存中中放值…………");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public void addContent(TbContent content) {

		Date data = new Date();
		content.setUpdated(data);
		content.setUpdated(data);
		contentMapper.insert(content);
		client.hdel(CONTENT_LIST, content.getCategoryId()+"");
	}

}
