/**
 * 
 */
package com.chao.pojo;

import java.io.Serializable;
import java.util.List;

/**  
* Title: EasyUIResult.java  
* @author ChaoSir 
* @date 2019年9月17日  
* @version 1.0  
*/
public class EasyUIResult implements Serializable {

	private Long total;
	
	private List<?> rows;

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}
	
	
	
}
