package com.mystudy;

import java.util.ArrayList;
import java.util.List;

import com.mystudy.po.Car;
import com.mystudy.po.Tyre;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {
	private static String ip = "127.0.0.1";
	private static int port = 6379;
	private static int timeout = 1000;
	private static String password = "1234567";
	private static int db = 0;
	private static String charset = "UTF-8";
	private static JedisPool jedispool;
	/**
	 * 初始化Redis连接池
	 */
	static {
		try {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(1000);
			config.setMaxIdle(300);
			config.setMaxWaitMillis(2000L);
			config.setTestOnBorrow(false);
			jedispool = new JedisPool(config, ip, port, timeout, password, db);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取Jedis实例
	 * 
	 * @return
	 */
	public synchronized static Jedis getJedis() throws Exception{
			if (jedispool != null) {
				Jedis resource = jedispool.getResource();
				return resource;
			}
			return null;
	}

	/**
	 * 释放jedis资源
	 * 
	 * @param jedis
	 */
	public static void returnResource(final Jedis jedis) {
		if (jedis != null) {
			jedispool.returnResource(jedis);
		}
	}

	public static void set(String key, Object obj) throws Exception {
		getJedis().set(key.getBytes(charset), JavaSerialUtil.serial(obj));
	}

	public static byte[] get(String key) throws Exception {
		return getJedis().get(key.getBytes(charset));
	}

	public static void main(String[] args) throws Exception {
		Car car = new Car();
		car.setName("hello");
		car.setFlag("world");
		car.setLength(3.0);
		car.setWidth(1.5);
		car.setHeight(1.2);

		Tyre tyre1 = new Tyre("front1", 1, 10.2);
		Tyre tyre2 = new Tyre("front2", 2, 10.2);
		List<Tyre> tyrelist = new ArrayList<Tyre>();
		tyrelist.add(tyre1);
		tyrelist.add(tyre2);
		car.setTyrelist(tyrelist);

		System.out.println(car);
		set("XKLC_1", car);
		byte[] b = get("XKLC_1");
		Object obj = JavaSerialUtil.deserial(b);
		System.out.println(obj);
	}


}
