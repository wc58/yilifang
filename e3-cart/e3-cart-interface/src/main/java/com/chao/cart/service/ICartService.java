/**
 * 
 */
package com.chao.cart.service;

import java.util.List;

import com.chao.pojo.TbItem;
import com.chao.utils.E3Result;

/**  
* Title: ICartService.java  
* @author ChaoSir 
* @date 2019年10月15日  
* @version 1.0  
*/
public interface ICartService {

	
	E3Result addCart(Long userId,List<TbItem> item);
	E3Result addCart(Long userId,Long itemId,Integer num);

	E3Result getCarList(Long usrId);
	void updateCart(Long userId,Long itemId,Integer num);
	void deleteCart(Long userId,String itemId);
	/**
	 * @param userId
	 * @param itemId
	 */
	
	
}
