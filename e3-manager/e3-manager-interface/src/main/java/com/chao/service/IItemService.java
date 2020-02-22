/**
 * 
 */
package com.chao.service;

import com.chao.pojo.EasyUIResult;
import com.chao.pojo.TbItem;

/**  
* Title: IItem.java  
* @author ChaoSir 
* @date 2019年9月8日  
* @version 1.0  
*/
public interface IItemService {

		public TbItem getItemById(long id);
	
		public EasyUIResult getPageList(Integer page,Integer rows);
		
}
