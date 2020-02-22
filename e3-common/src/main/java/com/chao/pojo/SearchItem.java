/**
 * 
 */
package com.chao.pojo;

import java.io.Serializable;

/**
 * Title: SearchPojo.java
 * 
 * @author ChaoSir
 * @date 2019年10月1日
 * @version 1.0
 */
public class SearchItem implements Serializable {

	

	private String id;
	private String title;
	private String sell_point;
	private long price;
	private String image;
	private String category_name;
	private String[] images;
	
	public String[] getImages() {
		String image = this.getImage();
		if (image != null && image != "") {
			String[] split = image.split(",");
			setImages(split);
		}
		return images;
	}
	public void setImages(String[] images) {
		this.images = images;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSell_point() {
		return sell_point;
	}

	public void setSell_point(String sell_point) {
		this.sell_point = sell_point;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	@Override
	public String toString() {
		return "SearchItem [id=" + id + ", title=" + title + ", sell_point=" + sell_point + ", price=" + price
				+ ", image=" + image + ", category_name=" + category_name + "]";
	}

}
