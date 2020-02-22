/**
 * 
 */
package com.chao.jedis;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

/**  
* Title: JedisTest.java  
* @author ChaoSir 
* @date 2019年9月29日  
* @version 1.0  
*/
public class JedisTest2 {
	

	@Test
	public void jedis() throws Exception {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-jedis.xml");
		JedisClient jedis = applicationContext.getBean(JedisClient.class);
		String set = jedis.set("chao", "超哥哥");
		String string = jedis.get("chao");
		System.out.println("set = " + set + "chao = " +string);
	}
	
	
}
