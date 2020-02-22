/**
 * 
 */
package com.chao.freemarker;

/**  
* Title: ChaoPojo.java  
* @author ChaoSir 
* @date 2019年10月10日  
* @version 1.0  
*/
public class ChaoPojo {

	private String id;
	private String name;
	private String age;
	public ChaoPojo(String string, String name, String age) {
		super();
		this.id = string;
		this.name = name;
		this.age = age;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	@Override
	public String toString() {
		return "ChaoPojo [id=" + id + ", name=" + name + ", age=" + age + "]";
	}
	
	
	
}
