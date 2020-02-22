/**
 * 
 */
package com.chao.order.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.chao.jedis.JedisClient;
import com.chao.mapper.TbOrderItemMapper;
import com.chao.mapper.TbOrderMapper;
import com.chao.mapper.TbOrderShippingMapper;
import com.chao.order.pojo.OrderInfo;
import com.chao.order.service.IOrderService;
import com.chao.pojo.TbOrder;
import com.chao.pojo.TbOrderItem;
import com.chao.pojo.TbOrderShipping;
import com.chao.utils.E3Result;

/**  
* Title: OrderServiceImpl.java  
* @author ChaoSir 
* @date 2019年10月18日  
* @version 1.0  
*/
@Service
public class OrderServiceImpl implements IOrderService {

	@Value("${ORDER_GEN_ID}")
	private String ORDER_GEN_ID;
	@Value("${ORDER_BEGIN_ID}")
	private String ORDER_BEGIN_ID;
	@Value("${ORDER_ITEM_GEN_ID}")
	private String ORDER_ITEM_GEN_ID;
	
	@Autowired
	private JedisClient  client;
	
	@Autowired
	private TbOrderMapper  orderMapper;
	@Autowired
	private TbOrderItemMapper  itemMapper;
	@Autowired
	private TbOrderShippingMapper  orderShippingMapper;
	
	@Override
	public E3Result createOrder(OrderInfo orderInfo) {
		
		//使用redis生产id
		if (!client.exists(ORDER_GEN_ID)) {
			client.set(ORDER_GEN_ID, ORDER_BEGIN_ID);
		}
		//设置为redis生成的id
		String orderId = client.incr(ORDER_GEN_ID).toString();
		orderInfo.setOrderId(orderId);
		//完善order数据
		Date date = new Date();
		orderInfo.setCreateTime(date);
		orderInfo.setUpdateTime(date);
		orderInfo.setStatus(1);	//1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭',
		
		//向order表插入数据
		orderMapper.insert(orderInfo);
		//向orderItem表插入数据
		List<TbOrderItem> items = orderInfo.getOrderItems();
		for (TbOrderItem item : items) {
			//生成id
			String orderItemId = client.incr(ORDER_ITEM_GEN_ID).toString();
			item.setId(orderItemId);
			item.setOrderId(orderId);
			itemMapper.insert(item);
		}
		// 向ordershipping表插入数据
		TbOrderShipping shipping = orderInfo.getOrderShipping();
		shipping.setOrderId(orderId);
		orderShippingMapper.insert(shipping);
		
		return E3Result.ok(orderId);
	}

}
