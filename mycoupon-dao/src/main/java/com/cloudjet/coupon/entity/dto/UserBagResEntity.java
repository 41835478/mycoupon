package com.cloudjet.coupon.entity.dto;

import java.io.Serializable;
import java.util.Date;

public class UserBagResEntity implements Serializable{

	/**
	 * 用户券包详情
	 */
	private static final long serialVersionUID = 1L;

	private String userBagId;//用户券包id	
	private Integer status;//用户券包中优惠券状态
	private String cpId;//优惠券id
	private String cpName;//优惠券名称
	private Integer isCode;//是否是券码优惠券  0,不是； 1,是 
	private Integer isBindCode;//是否绑定券码，0 未绑定，1 已绑定
	private Date beginTime;//券使用开始时间
	private Date dueTime;// 券使用过期时间
	private Date createTime;//券领取时间
	private String code;//券码
	private Integer isMsg;//是否发送短信,0不发，1发送
	private Integer msgStatus;//短信发送状态，0 发送成功，1发送失败 
	
	public Integer getIsBindCode() {
		return isBindCode;
	}
	public void setIsBindCode(Integer isBindCode) {
		this.isBindCode = isBindCode;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getUserBagId() {
		return userBagId;
	}
	public void setUserBagId(String userBagId) {
		this.userBagId = userBagId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCpId() {
		return cpId;
	}
	public void setCpId(String cpId) {
		this.cpId = cpId;
	}
	public String getCpName() {
		return cpName;
	}
	public void setCpName(String cpName) {
		this.cpName = cpName;
	}
	public Integer getIsCode() {
		return isCode;
	}
	public void setIsCode(Integer isCode) {
		this.isCode = isCode;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getDueTime() {
		return dueTime;
	}
	public void setDueTime(Date dueTime) {
		this.dueTime = dueTime;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getIsMsg() {
		return isMsg;
	}
	public void setIsMsg(Integer isMsg) {
		this.isMsg = isMsg;
	}
	public Integer getMsgStatus() {
		return msgStatus;
	}
	public void setMsgStatus(Integer msgStatus) {
		this.msgStatus = msgStatus;
	}
	
}
