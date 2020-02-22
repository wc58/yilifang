/**
 * 
 */
package com.chao.user.service;

import com.chao.utils.E3Result;

/**  
* Title: ICheckUser.java  
* @author ChaoSir 
* @date 2019年10月12日  
* @version 1.0  
*/
public interface ICheckUserService {

	//校验数据
	E3Result checkUser(String content,Integer type);
	
}
