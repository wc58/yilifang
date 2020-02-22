/**
 * 
 */
package com.chao.user.service;

import com.chao.pojo.TbUser;
import com.chao.utils.E3Result;

/**  
* Title: IUserService.java  
* @author ChaoSir 
* @date 2019年10月12日  
* @version 1.0  
*/
public interface IUserService {

	//添加用户
	E3Result createUser(TbUser tbUser);
	//登录
	E3Result loginUser(String username, String password);
	//查询用户
	E3Result getUserByCoken(String token);
	//退出登录
	E3Result logoout(String token);
	
}
