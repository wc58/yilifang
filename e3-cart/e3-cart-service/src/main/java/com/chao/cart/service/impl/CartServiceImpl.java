/**
 * 
 */
package com.chao.cart.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.cache.decorators.FifoCache;
import org.noggit.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.remoting.Client;
import com.chao.cart.service.ICartService;
import com.chao.jedis.JedisClient;
import com.chao.mapper.TbItemMapper;
import com.chao.pojo.TbItem;
import com.chao.utils.E3Result;
import com.chao.utils.JsonUtils;
import com.sun.tools.internal.xjc.model.CElement;

/**  
* Title: CartServiceImpl.java  
* @author ChaoSir 
* @date 2019年10月15日  
* @version 1.0  
*/
@Service
public class CartServiceImpl implements ICartService  {
	
	@Autowired
	private JedisClient  client;
	@Autowired
	private TbItemMapper  itemMapper;
	
	@Value("${USER_KEY}")
	private String USER_KEY;

	/**
	 * cookie数据转移到账户中
	 */
	@Override
	public E3Result addCart(Long userId, List<TbItem> item) {
		
		//遍历
		for (TbItem tbItem : item) {
			//判断商品是否存在
			if (client.hexists(USER_KEY +":"+userId, tbItem.getId()+"")) {
				//取出原数据
				String string = client.hget(USER_KEY +":"+userId, tbItem.getId()+"");
				//转型
				TbItem jsonToPojo = JsonUtils.jsonToPojo(string, TbItem.class);
				//数量相加
				jsonToPojo.setNum(tbItem.getNum() + jsonToPojo.getNum());
				//写会数据
				client.hset(USER_KEY +":"+userId, tbItem.getId()+"",JsonUtils.objectToJson(jsonToPojo));
				return E3Result.ok();
			}
			
			//设置加入为1
			tbItem.setNum(1);
			//不存在
			client.hset(USER_KEY +":"+userId, tbItem.getId()+"",JsonUtils.objectToJson(tbItem));
		}
		
		return E3Result.ok();
	}

	/**
	 * 向用户中添加数据
	 */
	@Override
	public E3Result addCart(Long userId, Long itemId, Integer num) {
		
		//判断商品是否存在
		if (client.hexists(USER_KEY +":"+userId, itemId+"")) {
			//取出原数据
			String string = client.hget(USER_KEY +":"+userId, itemId +"");
			//转型
			TbItem jsonToPojo = JsonUtils.jsonToPojo(string, TbItem.class);
			//数量相加
			jsonToPojo.setNum(jsonToPojo.getNum() + num);
			//写会数据
			client.hset(USER_KEY +":"+userId, itemId+"",JsonUtils.objectToJson(jsonToPojo));
			return E3Result.ok(); 
		}
		
		//根据商品id查找
		TbItem selectByPrimaryKey = itemMapper.selectByPrimaryKey(itemId);
		selectByPrimaryKey.setNum(1);
		//不存在
		client.hset(USER_KEY +":"+userId, itemId+"",JsonUtils.objectToJson(selectByPrimaryKey));
		String objectToJson = JsonUtils.objectToJson(selectByPrimaryKey);
		System.out.println(selectByPrimaryKey);
		
		return E3Result.ok(); 
	}

	/**
	 * 根据用户，查找对应的商品列表
	 */
	@Override
	public E3Result getCarList(Long usrId) {
		
		//保存转型后的对象
		List<TbItem> items = new ArrayList<TbItem>();
		//判断id不为空
		if (usrId != 0 && usrId !=null) {
			//根据id查找redis
			List<String> list = client.hvals(USER_KEY +":"+usrId);
			//转型
			for (String string : list) {
				TbItem item = JsonUtils.jsonToPojo(string, TbItem.class);
				items.add(item);
			}
		}
		//封装返回
		return E3Result.ok(items);
	}

	/**
	 * 更新数量
	 */
	@Override
	public void updateCart(Long userId, Long itemId, Integer num) {
		
		//判断商品是否存在
		if (client.hexists(USER_KEY +":"+userId,itemId + "")) {
			//取原数据
			String string = client.hget(USER_KEY +":"+userId,itemId + "");
			//转型
			TbItem item = JsonUtils.jsonToPojo(string, TbItem.class);
			//修改数据
			item.setNum(num);
			//写回数据
			client.hset(USER_KEY +":"+userId,itemId + "",JsonUtils.objectToJson(item));
		}
		//否 则不做处理
		
	}

	/**
	 * 删除用户所指定的商品
	 */
	@Override
	public void deleteCart(Long userId, String itemId) {
		
		//判断商品是否存在
		if (client.hexists(USER_KEY +":"+userId,itemId)) {
			//存在直接删除
			client.hdel(USER_KEY +":"+userId,itemId);
		}
		
	}

}
