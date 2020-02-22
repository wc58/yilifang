/**
 * 
 */
package com.chao.search.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**  
* Title: HandlerExceptionResolver.java  
* @author ChaoSir 
* @date 2019年10月6日  
* @version 1.0  
*/
public class GlobalExceptionResolver implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		
		System.out.println("系统发生异常！！！");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("message", "系统出现了错误！！！请稍后尝试……");
		modelAndView.setViewName("error/exception2");
		ex.printStackTrace();
		return modelAndView;
	}

}
