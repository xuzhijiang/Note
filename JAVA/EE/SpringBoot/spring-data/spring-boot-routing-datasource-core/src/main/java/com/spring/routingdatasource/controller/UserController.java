package com.spring.routingdatasource.controller;

import com.spring.routingdatasource.context.RoutingDataSourceContext;
import com.spring.routingdatasource.context.RoutingWith;
import com.spring.routingdatasource.domain.User;
import com.spring.routingdatasource.request.UserReq;
import com.spring.routingdatasource.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * User service which uses primary data source.
 */
@RestController
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping("/api/users")
	public User createUser(@RequestBody UserReq req) {
		return userService.createUser(req);
	}

	@GetMapping("/api/users")
	@RoutingWith(RoutingDataSourceContext.SLAVE)
	public List<User> getUsers() {
		return userService.getUsers();
	}

	@GetMapping("/api/users/{id}")
	@RoutingWith(RoutingDataSourceContext.SLAVE)
	public User getUser(@PathVariable("id") String id) {
		return userService.getUser(id);
	}

}
