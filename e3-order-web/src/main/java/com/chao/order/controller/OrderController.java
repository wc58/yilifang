/**
 * 
 */
package com.chao.order.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chao.cart.service.ICartService;
import com.chao.order.pojo.OrderInfo;
import com.chao.order.service.IOrderService;
import com.chao.pojo.TbOrderItem;
import com.chao.pojo.TbUser;
import com.chao.utils.E3Result;

/**  
* Title: OrderController.java  
* @author ChaoSir 
* @date 2019年10月16日  
* @version 1.0  
*/
@Controller
@Scope("prototype")
public class OrderController {

	@Autowired
	private ICartService  cartService;
	@Autowired
	private IOrderService  orderService;
	
	@RequestMapping("/order/order-cart")
	public String toOrder(HttpServletRequest request) {
		
		TbUser user = (TbUser) request.getAttribute("user");
		//调用商品列表
		E3Result result = cartService.getCarList(user.getId());
		//设置参数
		request.setAttribute("cartList", result.getData());
		
		return "order-cart";
	}
	
	@RequestMapping("/order/create")
	public String success(OrderInfo orderInfo,HttpServletRequest request) {
		
		//补全用户信息
		TbUser user = (TbUser) request.getAttribute("user");
		orderInfo.setUserId(user.getId());
		orderInfo.setBuyerNick(user.getUsername());
		//调用service创建订单
		E3Result e3Result = orderService.createOrder(orderInfo);
		
		//删除已下单的商品
		List<TbOrderItem> items = orderInfo.getOrderItems();
		for (TbOrderItem item : items) {
			cartService.deleteCart(user.getId(), item.getItemId());
		}
		
		//取订单号
		String orderId = (String) e3Result.getData();
		//写回订单号，总价格
		request.setAttribute("orderId", orderId);
		request.setAttribute("payment", orderInfo.getPayment());
		
		return "success";
	}
	
}
