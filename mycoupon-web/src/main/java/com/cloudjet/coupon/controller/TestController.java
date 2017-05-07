package com.cloudjet.coupon.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cloudjet.coupon.bean.User;
import com.cloudjet.coupon.core.Response;
import com.cloudjet.coupon.service.UserService;

@RestController
@EnableAutoConfiguration
public class TestController {
	
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/home")
	Response<String> home() {
		logger.error("test");
        return Response.success(null);
    }
	
	@RequestMapping(value="/user", method=RequestMethod.POST)
	 @ResponseBody Response<User> user(@RequestBody User user) {
		
		user = new User();
		user.setUsername("12哦哦");
		user.setPassword("1234");
		
		try{
			userService.save(user);
		}catch(Exception e){
			logger.error("插入用户报错=[Result:{}]", e.getMessage());
			return new Response<User>(1500, e.getMessage(), null);
		}
        return Response.success(user);
    }


	
}
