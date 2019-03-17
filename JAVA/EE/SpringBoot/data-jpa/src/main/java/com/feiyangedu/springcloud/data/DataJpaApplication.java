package com.feiyangedu.springcloud.data;

import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.feiyangedu.springcloud.data.domain.User;
import com.feiyangedu.springcloud.data.repository.UserRepository;
import com.feiyangedu.springcloud.data.request.UserReq;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 在实际开发过程中，对数据库的操作无非就“增删改查”。语句都是类似的，
 * 开发人员需要写大量类似而枯燥的语句来完成业务逻辑。
 *
 * 为了解决这些大量枯燥的数据操作语句，我们第一个想到的是使用ORM框架，
 * 比如：Hibernate。通过整合Hibernate之后，我们以操作Java实体的方式最终将数据改变映射到数据库表中。
 *
 * Spring-data-jpa依赖于Hibernate
 */
@SpringBootApplication
@EnableJpaRepositories
@EnableSwagger2
@RestController
public class DataJpaApplication {

	@Autowired
	UserRepository userRepository;

	@PostConstruct
	public void init() {
		for (int i = 0; i < 25; i++) {
			createUser();
		}
	}

	@GetMapping(value = "/")
	@ResponseBody
	String home() {
		return "<h1>Hello World!</h1>";
	}

	@PostMapping("/api/users")
	public User createUser() {
		User user = new User();
		user.setEmail(randomString() + "@itranswarp.com");
		user.setName("Mr " + randomString());
		user.setGender(randomString().hashCode() % 2 == 0);
		userRepository.save(user);
		return user;
	}

	@PutMapping("/api/users/{id}")
	public User updateUser(@PathVariable("id") Long id, @RequestBody UserReq user) {
		User exist = userRepository.findOne(id);
		if (exist == null) {
			throw new EntityNotFoundException("User not found: " + id);
		}
		exist.setEmail(user.getEmail());
		exist.setGender(user.isGender());
		exist.setName(user.getName());
		userRepository.save(exist);
		return exist;
	}

	@DeleteMapping("/api/users/{id}")
	public boolean deleteUser(@PathVariable("id") Long id) {
		User exist = userRepository.findOne(id);
		if (exist != null) {
			userRepository.delete(id);
			return true;
		}
		return false;
	}

	@GetMapping("/api/users")
	public Page<User> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size) {
		return userRepository.findAll(new PageRequest(page, size));
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(DataJpaApplication.class, args);
	}

	private String randomString() {
		return UUID.randomUUID().toString();
	}

	@Bean
	public Docket userApi() {
		return new Docket(DocumentationType.SWAGGER_2).select().paths(PathSelectors.regex("^/api/.*$")).build();
	}
}
