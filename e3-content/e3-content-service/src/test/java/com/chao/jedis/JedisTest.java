/**
 * 
 */
package com.chao.jedis;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
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
public class JedisTest {

	@Test
	/*
	 * 单机版直接连接
	 */
	public void jedis() throws Exception {
		Jedis jedis = new Jedis("192.168.25.131", 6379);
		String set = jedis.set("chao", "超哥哥");
		String string = jedis.get("chao");
		System.out.println("set = " + set + "chao = " +string);
		jedis.close();
	}
	
	@Test
	/*
	 * 使用连接池连接
	 */
	public void jedisPool() throws Exception {
		JedisPool jedisPool = new JedisPool("192.168.25.131",6379);
		Jedis resource = jedisPool.getResource();
		String set = resource.set("chao1", "王超");
		if (set.equals("OK")) {
			System.out.println(resource.get("chao1"));
		}
		resource.close();
		jedisPool.close();
	}
	
	@Test
	/*
	 * 集群版
	 */
	public void jedisCluster() throws Exception {
		Set<HostAndPort> nodes = new HashSet<HostAndPort>();
		nodes.add(new HostAndPort("192.168.25.129",7001));
		nodes.add(new HostAndPort("192.168.25.129",7002));
		nodes.add(new HostAndPort("192.168.25.129",7003));
		nodes.add(new HostAndPort("192.168.25.129",7004));
		nodes.add(new HostAndPort("192.168.25.129",7005));
		nodes.add(new HostAndPort("192.168.25.129",7006));
		JedisCluster cluster = new JedisCluster(nodes);
		String set = cluster.set("chao", "aaa");
		if (set.equals("OK")) {
			System.out.println(cluster.get("chao"));
		}
		cluster.close();
	}
	
}
