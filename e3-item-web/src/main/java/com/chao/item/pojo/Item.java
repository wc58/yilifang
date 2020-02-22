/**
 * 
 */
package com.chao.item.pojo;

import com.chao.pojo.TbItem;

/**
 * Title: Item.java
 * 
 * @author ChaoSir
 * @date 2019年10月9日
 * @version 1.0
 */
public class Item extends TbItem {
	
	public String[] getImages (){
		
		String image = this.getImage();
		if (image!=null && image!="") {
			String[] split = image.split(",");
			return split;
		}
		
		
		return null;
	}

	public Item() {
	}

	public Item(TbItem tbItem) {
		this.setBarcode(tbItem.getBarcode());
		this.setCid(tbItem.getCid());
		this.setCreated(tbItem.getCreated());
		this.setId(tbItem.getId());
		this.setImage(tbItem.getImage());
		this.setNum(tbItem.getNum());
		this.setPrice(tbItem.getPrice());
		this.setSellPoint(tbItem.getSellPoint());
		this.setStatus(tbItem.getStatus());
		this.setTitle(tbItem.getTitle());
		this.setUpdated(tbItem.getUpdated());
	}

}
