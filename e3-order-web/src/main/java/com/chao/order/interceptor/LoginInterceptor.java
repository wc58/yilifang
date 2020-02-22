/**
 * 
 */
package com.chao.order.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.chao.user.service.IUserService;
import com.chao.utils.CookieUtils;
import com.chao.utils.E3Result;

/**
 * Title: LoginInterceptor.java
 * 
 * @author ChaoSir
 * @date 2019年10月16日
 * @version 1.0
 */
public class LoginInterceptor implements HandlerInterceptor {

	@Autowired
	private IUserService iUserService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// 通过cookie判断是否登录
		String string = CookieUtils.getCookieValue(request, "COOKIE_COKEN_KEY", true);
		// 已登录
		if (StringUtils.isNotBlank(string)) {
			// 检查用户信息是否过期
			E3Result e3Result = iUserService.getUserByCoken(string);
			if (e3Result.getStatus() == 200) {
				// 将用户添加到域中
				request.setAttribute("user", e3Result.getData());
				// 放行
				return true;
			}
		}
		//====================================
		//未登录
		// 当前的url地址
		String url = request.getRequestURL().toString();
		System.out.println(url);
		// 跳转到登录界面
		response.sendRedirect("http://localhost:8880/user/login.u?redirect=" + url);
		// 拦截
		return false;
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
