package com.redis.core.springboot.redistemplate;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableCaching
public class RedisCacheApplication {

	// 操作的k和v都是字符串类型的.(因为String我们经常操作,所以就单独抽取了这个StringRedisTemplate)
	@Autowired
	StringRedisTemplate stringRedisTemplate;

	@Autowired
	RedisTemplate redisTemplate;// 操作的k和v都是对象类型的.

	@Autowired
	@Qualifier("custom-redis-template")
	RedisTemplate<Object, User> customRedisTemplate;

	@GetMapping("/key/{key}")
	public String valueOf(@PathVariable("key") String key) {
		String value = stringRedisTemplate.opsForValue().get(key);
		System.out.println("value: " + value);
		if (value == null) {
			stringRedisTemplate.opsForValue().set(key, UUID.randomUUID().toString(), 10, TimeUnit.SECONDS);
			//给redis中key为key的value的末尾追加一个hello字符串
			stringRedisTemplate.opsForValue().append(key, "-append-value");
			value = stringRedisTemplate.opsForValue().get(key);
		}
		return value;
	}

	@GetMapping("/user/{name}")
	public String getUser(@PathVariable("name") String name){
		Object obj = customRedisTemplate.opsForValue().get(name);
		System.out.println("obj: " + obj);
		System.out.println("instanceof User: " + (obj instanceof User));
		if(obj == null || !(obj instanceof User)){
			User user = new User();
			user.setId(new Random().nextInt());
			user.setAge(new Random().nextInt(20));
			user.setName(name);
			// 默认保存对象，使用的是jdk序列化机制，序列化后的数据保存到redis中
			// 如果需要将对象以JSON的方式保存，可以将对象转为JSON,也可以改变默认的序列化规则
			customRedisTemplate.opsForValue().set(name, user);
			obj = user;
		}

		return obj.toString();
	}

	@GetMapping("/hi/{name}")
	public String hi(@PathVariable("name") String name) throws Exception {
		return slowHi(name);
	}

	@Cacheable("names")
	@GetMapping("/hello/{name}")
	public String hello(@PathVariable("name") String name) throws Exception {
		return slowHello(name);
	}

	@CacheEvict("names")
	@GetMapping("/remove/{name}")
	public boolean unhello(@PathVariable("name") String name) throws Exception {
		return true;
	}

	String slowHi(String name) throws Exception {
		Thread.sleep(3000);
		return "Hi, " + name + "!";
	}

	String slowHello(String name) throws Exception {
		Thread.sleep(3000);
		return "Hello, " + name + "!";
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(RedisCacheApplication.class, args);
	}

}
