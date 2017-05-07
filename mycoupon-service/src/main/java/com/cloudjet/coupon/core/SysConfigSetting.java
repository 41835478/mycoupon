package com.cloudjet.coupon.core;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:config.properties")
public class SysConfigSetting {
	@Value("${msgURL}")
	private String msgURL;
	@Value("${levelURL}")
	private String levelURL;
	public String getMsgURL() {
		return msgURL;
	}
	public void setMsgURL(String msgURL) {
		this.msgURL = msgURL;
	}
	public String getLevelURL() {
		return levelURL;
	}
	public void setLevelURL(String levelURL) {
		this.levelURL = levelURL;
	}
	@Override
	public String toString() {
		return "SysConfigSetting [msgURL=" + msgURL + ", levelURL=" + levelURL + "]";
	}
	
	

}
