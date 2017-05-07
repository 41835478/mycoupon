package com.cloudjet.coupon.entity;

public class InfoCodePlanEntity extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 发布券码总数
	 */
	private int count;
	
	/**
	 * 商铺号
	 */
	private String platCode;
	
	/**
	 * 商铺名字
	 */
	private String shopName;
	
	/**
	 * 状态0，初始化；1，绑定券
	 */
	private Integer status;
	
	/**
	 * 优惠券
	 */
	private String cpId;
	
	/**
	 * 是否发送信息
	 */
	private String isMsg;
	
	/**
	 * 短信里面URL
	 */
	private String msgUrl;
	
	/**
	 * 短信模板
	 */
	private String msgTemplate;
	
	/**
	 * 短信参数，如 "券码："
	 */
	private String param;

	/**
	 * 导入的券码类型，0 通用 ， 1 链接
	 */
	private Integer type;
	
	/**
	 * 短信网关 标识
	 * */
	private String msgTag;
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getMsgUrl() {
		return msgUrl;
	}
	public void setMsgUrl(String msgUrl) {
		this.msgUrl = msgUrl;
	}
	public String getMsgTemplate() {
		return msgTemplate;
	}
	public void setMsgTemplate(String msgTemplate) {
		this.msgTemplate = msgTemplate;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getIsMsg() {
		return isMsg;
	}
	public void setIsMsg(String isMsg) {
		this.isMsg = isMsg;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getPlatCode() {
		return platCode;
	}
	public void setPlatCode(String platCode) {
		this.platCode = platCode;
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
	public String getMsgTag() {
		return msgTag;
	}
	public void setMsgTag(String msgTag) {
		this.msgTag = msgTag;
	}
	@Override
	public String toString() {
		return "InfoCodePlanEntity [count=" + count + ", platCode=" + platCode + ", shopName=" + shopName + ", status="
				+ status + ", cpId=" + cpId + ", isMsg=" + isMsg + ", msgUrl=" + msgUrl + ", msgTemplate=" + msgTemplate
				+ ", param=" + param + ", type=" + type + ", msgTag=" + msgTag + "]";
	}

	
}
