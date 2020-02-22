/**
 * 
 */
package com.chao.cart.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.chao.cart.service.ICartService;
import com.chao.pojo.TbItem;
import com.chao.pojo.TbUser;
import com.chao.user.service.ICheckUserService;
import com.chao.user.service.IUserService;
import com.chao.utils.CookieUtils;
import com.chao.utils.E3Result;
import com.chao.utils.JsonUtils;
import com.mchange.v1.identicator.Identicator;

/**
 * Title: LoginInterceptor.java
 * 
 * @author ChaoSir
 * @date 2019年10月15日
 * @version 1.0
 */
public class LoginInterceptor implements HandlerInterceptor {

	@Autowired
	private IUserService userServiceImpl;

	@Autowired
	private ICartService  cartService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// 取出token
		String string = CookieUtils.getCookieValue(request, "COOKIE_COKEN_KEY");
		// 判断是否存在
		if (StringUtils.isBlank(string)) {
			return true;
		}
		// 判断是否在登录
		E3Result result = userServiceImpl.getUserByCoken(string);
		if (result.getStatus() != 200) {
			return true;
		}
		TbUser user = (TbUser) result.getData();
		// 将用户信息保存到request
		request.setAttribute("user", user);
//===============================================================
		//获得所有cookie商品
		String CART_KEY = CookieUtils.getCookieValue(request, "CART_KEY",true);
		//cookie转换为对象
		if(StringUtils.isNotBlank(CART_KEY)) {
			List<TbItem> items = JsonUtils.jsonToList(CART_KEY, TbItem.class);
			//调用服务端将cookie保存到账户中
			E3Result e3Result = cartService.addCart(user.getId(), items);
			//删除本地cookie
			CookieUtils.deleteCookie(request, response, "CART_KEY");
		}
//===============================================================
		
		// 放行
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
