package com.cloudjet.coupon.model;

import java.io.Serializable;

public class InfoOperatorModel implements Serializable{
	
	/**
	 * 操作类型，0-创建,1-物理删除,2-逻辑删除,3-编辑,4-发放,5-冻结，6-补发
	 */
	
	public enum InfoOperatorType{
		/**
		 * 创建
		 * */
		init,
		/**
		 * 删除 物理
		 * */
		delete,
		/**
		 * 逻辑删除
		 * */
		cut,
		/**
		 * 编辑
		 * */
		edit,
		/**
		 * 发放
		 * */
		send,
		/**
		 * 冻结
		 * */
		freeze,
		/**
		 * 补发
		 * */
		reissue,
		/**
		 * 消息推送（分享满足条件）
		 * */
		msg
	}
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String tel;
	private String name;
	private String userId;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "InfoOperatorModel [id=" + id + ", tel=" + tel + ", name=" + name + ", userId=" + userId + "]";
	}

}
