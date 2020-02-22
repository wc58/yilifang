/**
 * 
 */
package com.chao.pojo;

import java.io.Serializable;

/**  
* Title: EasyUITreeNode.java  
* @author ChaoSir 
* @date 2019年9月18日  
* @version 1.0  
*/
public class EasyUITreeNode implements Serializable {

	private Long id;
	private String text;
	private String state;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
	
}
