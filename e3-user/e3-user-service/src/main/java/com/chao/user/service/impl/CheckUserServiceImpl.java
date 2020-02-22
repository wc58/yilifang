/**
 * 
 */
package com.chao.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chao.mapper.TbUserMapper;
import com.chao.pojo.TbUser;
import com.chao.pojo.TbUserExample;
import com.chao.pojo.TbUserExample.Criteria;
import com.chao.user.service.ICheckUserService;
import com.chao.utils.E3Result;

/**
 * Title: CheckUserServiceImpl.java
 * 
 * @author ChaoSir
 * @date 2019年10月12日
 * @version 1.0
 */
@Service
public class CheckUserServiceImpl implements ICheckUserService {

	@Autowired
	private TbUserMapper mapper;

	@Override
	public E3Result checkUser(String content, Integer type) {
		/*
		 创建用户表的查询条件
		根据类型对应查询
			1创建用户条件判断
			2创建电话号码判断
			3创建邮箱判断
		根据查询结果判断是否存在
			存在 true
			不存在 false
		 */
		//创建条件对象
		TbUserExample userExample = new TbUserExample();
		Criteria criteria = userExample.createCriteria();
		
		//1代表用户名
		//2代表电话号码
		//3代表邮箱
		if (type == 1) {
			criteria.andUsernameEqualTo(content);
		}else if (type == 2) {
			criteria.andPhoneEqualTo(content);
		}else if (type == 3) {
			criteria.andEmailEqualTo(content);
		}else {
			return E3Result.build(400, "非法格式！");
		}
		
		List<TbUser> users = mapper.selectByExample(userExample);
		//false 表示存在 则不可注册 
		//true 表示不存在 则可注册
		if (users.size() != 0 && users != null) {
			return E3Result.ok(false);
		}
		return E3Result.ok(true);
	}
}
