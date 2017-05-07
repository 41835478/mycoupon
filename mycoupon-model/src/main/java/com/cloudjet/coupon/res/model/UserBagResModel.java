package com.cloudjet.coupon.res.model;

import java.io.Serializable;
import java.util.Date;

public class UserBagResModel implements Serializable{

	/**
	 * 用户券包信息
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 优惠券是否是券码优惠券， 0 不是券码券，1券码券
	 */
	public enum CouponIsCode{
		no,
		yes
	}
	
	/**
	 * 券码优惠券是否绑定券码，0 未绑定，1 已绑定
	 */
	public enum CouponIsBindCode{
		no,
		yes
	}
	
	/**
	 * 用户券包状态0,已领取,被发放  1,已使用(付款)  2,已删除 3,已过期（没有数据库状态，根据日期判断返回model使用）
	 * 4，已使用(未付款)
	 * */
	public enum UserBagStatus{
		get,
		cost,
		del,
		expired,
		unpay
	}
	
	/**
     *  是否发送短信,0不发，1发送
	 */
	public enum CodeisMsg{
		no,
		yes
	}
	
	/**
	 * 是否成功发送短信，0 发送成功，1发送失败 
	 */
	public enum CodeMsgStatusEnum{
		/**
		 * 0,成功
		 */
		success, 
		/**
		 * 1,失败
		 */
		fail
	}

	/**
	 * 优惠券id
	 */
	private String cpId;
	
	/**
	 * 优惠券名称
	 * */
	private String cpName;

	/**
	 * 用户券包中优惠券状态
	 */
	private Integer status;
	
	/**
	 * 是否是券码优惠券  0,不是； 1,是 
	 * */
	private Integer isCode;
	
	/**
	 * 是否绑定券码
	 */
	private Integer isBindCode;
	
	/**
	 * 券码
	 */
	private String code;
	
	/**
	 * 券使用开始时间
	 * */
	private Date beginTime;
	
	/**
	 * 券使用过期时间
	 * */
	private Date dueTime;
	
	/**
	 * 券领取时间
	 */
	private Date createTime;

	/**
	 * 是否发送短信,0不发，1发送
	 */
	private Integer isMsg;
	
	/**
	 *  是否成功发送短信，0 发送成功，1发送失败 
	 */
	private Integer msgStatus;
	
	public Integer getIsBindCode() {
		return isBindCode;
	}

	public void setIsBindCode(Integer isBindCode) {
		this.isBindCode = isBindCode;
	}

	public Integer getIsMsg() {
		return isMsg;
	}

	public void setIsMsg(Integer isMsg) {
		this.isMsg = isMsg;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIsCode() {
		return isCode;
	}

	public void setIsCode(Integer isCode) {
		this.isCode = isCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getMsgStatus() {
		return msgStatus;
	}

	public void setMsgStatus(Integer msgStatus) {
		this.msgStatus = msgStatus;
	}

	@Override
	public String toString() {
		return "UserBagResModel [cpId=" + cpId + ", cpName=" + cpName + ", status=" + status + ", isCode=" + isCode
				+ ", isBindCode=" + isBindCode + ", code=" + code + ", beginTime=" + beginTime + ", dueTime=" + dueTime
				+ ", createTime=" + createTime + ", isMsg=" + isMsg + ", msgStatus=" + msgStatus + "]";
	}


}
