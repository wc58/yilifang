/**
 * 
 */
package com.chao.pojo;

import java.io.Serializable;
import java.util.List;

/**  
* Title: SearchResult.java  
* @author ChaoSir 
* @date 2019年10月2日  
* @version 1.0  
*/
public class SearchResult implements Serializable {

	private long recordCount;
	private int totalPages;
	private List<SearchItem> itemList;
	public long getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(long recordCount) {
		this.recordCount = recordCount;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public List<SearchItem> getItemList() {
		return itemList;
	}
	public void setItemList(List<SearchItem> itemList) {
		this.itemList = itemList;
	}
	
	
}
