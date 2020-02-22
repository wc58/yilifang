/**
 * 
 */
package com.chao.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chao.jedis.JedisClient;
import com.chao.mapper.TbItemDescMapper;
import com.chao.pojo.TbItemDesc;
import com.chao.service.IItemDescService;
import com.chao.utils.JsonUtils;

/**
 * Title: ItemDescImpl.java
 * 
 * @author ChaoSir
 * @date 2019年10月9日
 * @version 1.0
 */
@Service
public class ItemDescServiceImpl implements IItemDescService {

	@Autowired
	private TbItemDescMapper descMapper;

	@Autowired
	private JedisClient client;

	/**
	 * 查询商品的描述
	 */
	@Override
	public TbItemDesc geTbItemDesc(Long item_id) {

		try {
			// 读取缓存
			String string = client.get("ITEM_INOF_PRR" + item_id + ":DESC");
			if (StringUtils.isNotBlank(string)) {
				// 转型
				TbItemDesc itemDesc = JsonUtils.jsonToPojo(string, TbItemDesc.class);
				System.out.println("desc向缓存中读取数据……");
				return itemDesc;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		TbItemDesc itemDesc = descMapper.selectByPrimaryKey(item_id);

		// 写入缓存
		if (itemDesc != null) {
			try {
				// 转为json，保存数据
				client.set("ITEM_INOF_PRR" + item_id + ":DESC", JsonUtils.objectToJson(itemDesc));
				// 设置时期
				// client.expire("ITEM_INOF_PRR"+ item_id +"DESC", , ITEM_INFO_EXPIRE);
				System.out.println("desc向缓存中写入数据……");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return itemDesc;
	}

}
