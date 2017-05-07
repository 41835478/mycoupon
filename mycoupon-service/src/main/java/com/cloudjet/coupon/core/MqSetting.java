package com.cloudjet.coupon.core;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mq",locations = "classpath:config/mq_producer.properties")  
public class MqSetting {
	
	private String accessKey;
	private String secretKey;
	private String topic;
	private String producerId;
	private String tags;
	private String tagsPull;
	private String mqkey;
	
	public String getAccessKey() {
		return accessKey;
	}
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getProducerId() {
		return producerId;
	}
	public void setProducerId(String producerId) {
		this.producerId = producerId;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getMqkey() {
		return mqkey;
	}
	public void setMqkey(String mqkey) {
		this.mqkey = mqkey;
	}
	public String getTagsPull() {
		return tagsPull;
	}
	public void setTagsPull(String tagsPull) {
		this.tagsPull = tagsPull;
	}
	@Override
	public String toString() {
		return "MqSetting [accessKey=" + accessKey + ", secretKey=" + secretKey + ", topic=" + topic + ", producerId="
				+ producerId + ", tags=" + tags + ", tagsPull=" + tagsPull + ", mqkey=" + mqkey + "]";
	}
	
	
	
	
	
}
