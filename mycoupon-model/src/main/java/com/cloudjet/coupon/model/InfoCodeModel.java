package com.cloudjet.coupon.model;

import java.io.Serializable;
import java.util.List;

public class InfoCodeModel implements Serializable{

	/**
	 * 优惠码model
	 */
	private static final long serialVersionUID = 1L;
	
	
	public enum CodePlanStatus{
		
		/**
		 * 初始化
		 * */
		init,
		/**
		 * 已绑定
		 * */
		bind
		
	}
	
	public enum CodeStatus{
		
		/**
		 * 初始化
		 * */
		init,
		/**
		 * 已绑定(用户券包绑定)
		 * */
		bind,
		/**
		 * 已消费(使用)
		 * */
		cost
		
	}
	
	public enum CodeMsgStatus{
		/**
		 * 未发送短信
		 */
		no,
		/**
		 * 已发送短信
		 */
		yes
	}
	
	public enum CodeTypeEnum{
		/**
		 * 通用类型
		 */
		common,
		/**
		 * 链接类型
		 */
		link
	}
	
	/**
	 * 是否发短信，0 不发短信，1发短信
	 */
	private String msgStatus;
	
	/**
	 * 短信标示
	 * */
	private String  msgTag;
	
	/**
	 * 商铺号
	 */
	private String platCode;
	
	/**
	 * 商铺名字
	 */
	private String shopName;
	
	/**
	 * 导入的券码
	 */
	private List<String> infoCodes;
	
	/**
	 * 导入的券码类型，0 通用 ， 1 链接
	 */
	private Integer type;
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getMsgStatus() {
		return msgStatus;
	}
	public void setMsgStatus(String msgStatus) {
		this.msgStatus = msgStatus;
	}
	public String getPlatCode() {
		return platCode;
	}
	public void setPlatCode(String platCode) {
		this.platCode = platCode;
	}
	public List<String> getInfoCodes() {
		return infoCodes;
	}
	public void setInfoCodes(List<String> infoCodes) {
		this.infoCodes = infoCodes;
	}
	
	public String getMsgTag() {
		return msgTag;
	}
	public void setMsgTag(String msgTag) {
		this.msgTag = msgTag;
	}
	@Override
	public String toString() {
		return "InfoCodeModel [msgStatus=" + msgStatus + ", platCode=" + platCode + ", shopName=" + shopName
				+ ", infoCodes=" + infoCodes + ", type=" + type + "]";
	}
	
}
