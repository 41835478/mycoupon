package com.cloudjet.coupon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.cloudjet.coupon.core.MqSetting;


@ImportResource(locations={"classpath:application-persistance.xml","classpath:config/mq_producer.xml"})
@SpringBootApplication
@EnableConfigurationProperties({MqSetting.class})
public class WebApplication extends WebMvcConfigurerAdapter{

	public static void main(String[] args){
		SpringApplication.run(WebApplication.class, args);
	}
	

}