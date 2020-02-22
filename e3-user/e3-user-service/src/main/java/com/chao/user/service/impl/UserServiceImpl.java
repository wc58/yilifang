/**
 * 
 */
package com.chao.user.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.chao.jedis.JedisClient;
import com.chao.mapper.TbUserMapper;
import com.chao.pojo.TbUser;
import com.chao.pojo.TbUserExample;
import com.chao.pojo.TbUserExample.Criteria;
import com.chao.user.service.IUserService;
import com.chao.utils.E3Result;
import com.chao.utils.JsonUtils;

/**
 * Title: UserServiceImpl.java
 * 
 * @author ChaoSir
 * @date 2019年10月12日
 * @version 1.0
 */
@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private CheckUserServiceImpl checkUserServiceImpl;

	@Autowired
	private TbUserMapper userMapper;

	@Autowired
	private JedisClient client;

	@Override
	public E3Result createUser(TbUser tbUser) {
		/*
		 * 判断用户是否为空 空 结束警告 判断密码是否为空 空 结束警告 调用checkdata判断用户名是否存在 存在 true 结束警告
		 * 调用checkdata判断电话号码是否存在 存在 true 结束警告 调用checkdata判断邮箱是否存在 存在 true 结束警告
		 * 补充其他信息，创建时间，修改时间 保存用户 结束成功
		 */

		// 校验数据是否存在
		if (StringUtils.isBlank(tbUser.getUsername())) {
			return E3Result.build(400, "用户名不能为空！");
		}
		if (StringUtils.isBlank(tbUser.getPassword())) {
			return E3Result.build(400, "密码不能为空！");
		}

		// 判断以下是否存在，存在则不可注册
		// 返回false表示不可注册
		// 返回true表示可以注册
		if (!(boolean) checkUserServiceImpl.checkUser(tbUser.getUsername(), 1).getData()) {
			return E3Result.build(400, "此用户名已注册！");
		}
		if (StringUtils.isNotBlank(tbUser.getPhone())) {
			if (!(boolean) checkUserServiceImpl.checkUser(tbUser.getPhone(), 2).getData()) {
				return E3Result.build(400, "此电话已经注册！");
			}
		}
		if (StringUtils.isNotBlank(tbUser.getEmail())) {
			if (!(boolean) checkUserServiceImpl.checkUser(tbUser.getEmail(), 3).getData()) {
				return E3Result.build(400, "此用户已经注册！");
			}
		}

		// 填充其他剩余信息
		tbUser.setCreated(new Date());
		tbUser.setUpdated(new Date());

		// 为密码加密
		tbUser.setPassword(DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes()));

		// 保存用户
		userMapper.insert(tbUser);

		return E3Result.ok();
	}

	@Override
	public E3Result loginUser(String username, String password) {
		/*
		 * 判断用户名和密码是否为空 空 结束报错 根据用户名查找 结果为空 结束报错 不为空 取数据库密码和输入对比是否正确 不正确 结束报错
		 * 使用UUID获得token 密码设空 对象转json保存到redis中 设置过期时间
		 */

		// 校验数据是否正确
		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			return E3Result.build(400, "用户名和密码不能为空！");
		}
		// 创建查询条件，并设为查询用户名
		TbUserExample userExample = new TbUserExample();
		Criteria criteria = userExample.createCriteria();
		criteria.andUsernameEqualTo(username);

		// 执行查询
		List<TbUser> users = userMapper.selectByExample(userExample);

		// 判断是否存在
		if (users.size() == 0 || users == null) {
			return E3Result.build(400, "用户名或密码错误！");
		}

		TbUser user = users.get(0);
		if (!user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))) {
			return E3Result.build(400, "用户名或密码错误！");
		}

		// 创建token
		String token = UUID.randomUUID().toString();

		// 将密码设置为null，提高安全度
		user.setPassword(null);

		System.out.println(token);
		// 向redis中设置用户信息
		client.set("USER_INFO" + token, JsonUtils.objectToJson(user));
		// 设置保存时间
		client.expire("USER_INFO" + token, 60 * 30);
		return E3Result.ok(token);
	}

	@Override
	public E3Result getUserByCoken(String token) {
		/*
		 * 根据token查询redis 空 结束抱错 有 返回 重新设置过期时间
		 */
		// 取值
		String string = client.get("USER_INFO" + token);
		// 取不到说明过期，或不存在
		if (StringUtils.isBlank(string)) {
			return E3Result.build(400, "用户信息已过期，请重新登录……");
		}

		string = "." + string;//截字符串，防报错
		// 根据后缀.del判断是否删除
		if ("del".equals(string.substring(string.lastIndexOf(".") + 1))) {
			//删除 只返回用户名回显
			return E3Result.build(201, string.substring(1,string.lastIndexOf(".")));
		} else {
			//未删除 返回用户全部信息
			// 重置过期时间
			client.expire("USER_INFO" + token, 60 * 30);
			string = string.substring(string.lastIndexOf(".") + 1);
			// 没有删除直接返回全部信息
			TbUser user = JsonUtils.jsonToPojo(string, TbUser.class);
			return E3Result.ok(user);
		}
	}

	@Override
	public E3Result logoout(String token) {
		/*
		 * 根据token查询redis 空 结束抱错 有 返回 重新设置过期时间
		 */
		// 根据coken删除记录
//		Long del = client.del("USER_INFO"+token);
		
		//获得原数据
		String string = client.get("USER_INFO" + token);
		//转为对象
		TbUser user = JsonUtils.jsonToPojo(string, TbUser.class);
		//设置为只有用户名		del后缀表示已经删除
		String set = client.set("USER_INFO" + token, user.getUsername() + ".del");
		// 若为不为1则，删除失败
		if (!set.equals("OK")) {
			return E3Result.build(400, "退出异常！");
		}

		return E3Result.ok("退出成功！");
	}

}
