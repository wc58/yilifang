/**
 * 
 */
package com.chao.order.pojo;

import java.util.List;

import com.chao.pojo.TbOrder;
import com.chao.pojo.TbOrderItem;
import com.chao.pojo.TbOrderShipping;

/**  
* Title: OrderInfo.java  
* @author ChaoSir 
* @date 2019年10月18日  
* @version 1.0  
*/
public class OrderInfo extends TbOrder {

	private List<TbOrderItem> orderItems;
	private TbOrderShipping orderShipping;
	public List<TbOrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<TbOrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	public TbOrderShipping getOrderShipping() {
		return orderShipping;
	}
	public void setOrderShipping(TbOrderShipping orderShipping) {
		this.orderShipping = orderShipping;
	}
	
	
}
