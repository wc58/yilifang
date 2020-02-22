/**
 * 
 */
package com.chao.order.service;

import com.chao.order.pojo.OrderInfo;
import com.chao.utils.E3Result;

/**  
* Title: IOrder.java  
* @author ChaoSir 
* @date 2019年10月18日  
* @version 1.0  
*/
public interface IOrderService {

	/**
	 * 创建订单
	 * @param orderInfo
	 * @return
	 */
	E3Result createOrder(OrderInfo orderInfo);
	
}
