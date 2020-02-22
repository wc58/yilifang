/**
 * 
 */
package com.chao.cart.controller;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SNIHostName;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.ls.LSException;

import com.chao.cart.service.ICartService;
import com.chao.pojo.TbItem;
import com.chao.pojo.TbUser;
import com.chao.service.IItemService;
import com.chao.utils.CookieUtils;
import com.chao.utils.E3Result;
import com.chao.utils.JsonUtils;

/**
 * Title: CartController.java
 * 
 * @author ChaoSir
 * @date 2019年10月14日
 * @version 1.0
 */
@Controller
@Scope("prototype")
public class CartController {

	@Value("${TT_CART}")
	private String TT_CART;// key值

	@Value("${COOKIE_SAVE_TIME}")
	private Integer COOKIE_SAVE_TIME;// key保存时间

	@Autowired
	private IItemService iItemService;

	@Autowired
	private ICartService cartService;

	/**
	 * 将订单加入购物车
	 * 
	 * @param itemId
	 * @param num
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	@RequestMapping("cart/add/{itemId}")
	public String addCartItem(@PathVariable Long itemId, @RequestParam(defaultValue = "1") Integer num,
			HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		// 通过拦截器，判断是否登录
		TbUser user = (TbUser) httpServletRequest.getAttribute("user");
		if (user != null) {
			// 调用服务端，保存到账户中
			E3Result e3Result = cartService.addCart(user.getId(), itemId, num);
			return "cartSuccess";
		}

		// 获得所有cookie的订单列表
		List<TbItem> list = getItemListByCookie(httpServletRequest);
		Boolean flag = true; // 是否为新订单 默认为新订单
		// 判断加入购物车的订单是否存在
		for (TbItem tbItem : list) {
			// 应该比较值
			if (tbItem.getId() == itemId.longValue()) {
				// 获得原来数量，再加
				tbItem.setNum(tbItem.getNum() + num);
				// 修改为false，不是新订单
				flag = false;
				// 最后跳出
				break;
			}
		}

		// 判断是否是新订单 是则重新添加个订单
		if (flag) {
			// 根据id查找对应的商品
			TbItem item = iItemService.getItemById(itemId);
			// 设置图片
			String image = item.getImage();
			if (StringUtils.isNotBlank(image)) {
				String[] images = image.split(",");
				item.setImage(images[0]);
			}
			// 设置数量
			item.setNum(1);
			// 将新订单添加到订单列表中
			list.add(item);
		}

		// 写入到客户端
		CookieUtils.setCookie(httpServletRequest, httpServletResponse, TT_CART, JsonUtils.objectToJson(list),
				COOKIE_SAVE_TIME, true);

		return "cartSuccess";
	}

	/**
	 * 显示商品列表
	 * 
	 * @param httpServletRequest
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/cart/cart")
	public String cartList(HttpServletRequest httpServletRequest) {
		// 返回的商品列表
		List<TbItem> list = null;
		// 取user对象
		TbUser user = (TbUser) httpServletRequest.getAttribute("user");
		// 对象不为空，表登录 查数据库
		if (user != null) {
			// 查询redis
			E3Result carList = cartService.getCarList(user.getId());
			// 取值
			if (carList.getData() != null) {
				list = (List<TbItem>) carList.getData();
			}
		} else {
			// 对象为空，表离线 查cookie
			String cookieValue = CookieUtils.getCookieValue(httpServletRequest, TT_CART, true);
			if (StringUtils.isNotBlank(cookieValue)) {
				list = JsonUtils.jsonToList(cookieValue, TbItem.class);
			}
		}
		// 响应到前端
		httpServletRequest.setAttribute("cartList", list);
		return "cart";
	}

	/**
	 * 获得cookie中的订单列表
	 * 
	 * @param httpServletRequest
	 * @return
	 */
	public List<TbItem> getItemListByCookie(HttpServletRequest httpServletRequest) {

		// 取购物车列表
		String cookieValue = CookieUtils.getCookieValue(httpServletRequest, TT_CART, true);
		// 判断是否为空
		if (StringUtils.isBlank(cookieValue)) {
			return new ArrayList<TbItem>();
		}
		// 转换为item集合
		List<TbItem> jsonToList = JsonUtils.jsonToList(cookieValue, TbItem.class);
		return jsonToList;
	}

	/**
	 * 修改商品数量
	 * 
	 * @param itemId
	 * @param num
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/cart/update/num/{itemId}/{num}")
	@ResponseBody
	public E3Result updateItem(@PathVariable Long itemId, @PathVariable Integer num, HttpServletRequest request,
			HttpServletResponse response) {

		// 通过拦截器判断是否登录
		TbUser user = (TbUser) request.getAttribute("user");
		if (user != null) {
			// 调用数据库更新数量
			cartService.updateCart(user.getId(), itemId, num);
		} else {
			// 获得所有商品
			List<TbItem> list = getItemListByCookie(request);
			// 遍历查找
			for (TbItem tbItem : list) {
				// 根据判断
				if (tbItem.getId().longValue() == itemId) {
					// 设置数量
					tbItem.setNum(num);
					// 跳出
					break;
				}
			}
			// 重写cookie
			CookieUtils.setCookie(request, response, TT_CART, JsonUtils.objectToJson(list), COOKIE_SAVE_TIME, true);
		}
		// 返回
		return E3Result.ok();
	}

	@RequestMapping("/cart/delete/{itemId}")
	public String delteItem(@PathVariable Long itemId, HttpServletRequest request, HttpServletResponse response) {

		// 通过拦截器判断是否登录
		TbUser user = (TbUser) request.getAttribute("user");
		if (user != null) {
			// 调用数据库删除
			cartService.deleteCart(user.getId(), itemId+"");
		} else {
			// 获取所有商品
			List<TbItem> list = getItemListByCookie(request);
			// 遍历查找
			for (TbItem tbItem : list) {
				// 根据id判断
				if (tbItem.getId().longValue() == itemId) {
					// 删除该商品
					list.remove(tbItem);
					// 跳出
					break;
				}
			}
			// 重写cookie
			CookieUtils.setCookie(request, response, TT_CART, JsonUtils.objectToJson(list), COOKIE_SAVE_TIME, true);
		}
		// 重定向到商品列表
		return "redirect:/cart/cart.html";
	}

}
