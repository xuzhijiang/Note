package com.didispace;

import com.didispace.domain.User;
import com.didispace.domain.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 注解配置与EhCache使用
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class ApplicationTests {

	@Autowired
	private UserRepository userRepository;

	// 为了可以更好的观察，缓存的存储，我们可以在单元测试中注入cacheManager。
	// 使用debug模式运行单元测试，观察cacheManager中的缓存集users以及其中的User对象的缓存加深理解。
	@Autowired
	private CacheManager cacheManager;

	/**
	 * 初始化插入User表一条用户名为AAA，年龄为10的数据
	 */
	@Before
	public void before() {
		userRepository.save(new User("AAA", 10));
	}

	@Test
	public void test() throws Exception {

		// 并通过findByName函数完成两次查询。
		User u1 = userRepository.findByName("AAA");
		System.out.println("第一次查询：" + u1.getAge());

		User u2 = userRepository.findByName("AAA");
		System.out.println("第二次查询：" + u2.getAge());

		u1.setAge(20);
		userRepository.save(u1);
		User u3 = userRepository.findByName("AAA");
		System.out.println("第三次查询：" + u3.getAge());

	}

}
