package com.cloudjet.coupon.entity.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class UserBagFindEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userBagId;//券包id
	private String cpId; //优惠券主键
	private String cpName;//优惠劵名字
	private Date beginTime;//优惠劵使用日期
	private Date dueTime;//优惠劵使用日期
	private String type;//优惠券优惠类型 zero，无门槛；reduce，满减 ；discount，折扣券
	private String rule;//优惠券使用规则
	private String memo;//优惠券备注
	private String conditionId;//优惠券条件id
	
	private Integer couponType;//优惠券类型 0,平台优惠券；1商品优惠券
	/**
	 * 面值 折扣
	 * */
	private Double par;
	/**
	 * 区间金额开始
	 * */
	private Double startFee;
	/**
	 * 区间金额 结束
	 * */
	private Double endFee;
	
	/**
	 * 当优惠券是商品优惠券包括的商品列表(品类券包含的商品id)
	 * */
	private List<String> productIds;
	
	/**
	 * code码
	 * */
	private String cpCode;
	/**
	 * 富文本
	 * */
	private String richText;
	/**
	 * url
	 * */
	private String url;
	/**
	 * url button
	 * */
	private String urlButton;
	
	/**
	 * 是否是券码券
	 * */
	private Integer isCode;
	private Integer meanwhile;//是否能与平台券同时使用 0,否，1是   大范围
	private Integer meantime;//是否与券同时使用   0否 1是   小范围
	
	
	public String getCpName() {
		return cpName;
	}
	public void setCpName(String cpName) {
		this.cpName = cpName;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCpId() {
		return cpId;
	}
	public void setCpId(String cpId) {
		this.cpId = cpId;
	}
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getConditionId() {
		return conditionId;
	}
	public void setConditionId(String conditionId) {
		this.conditionId = conditionId;
	}
	public Integer getCouponType() {
		return couponType;
	}
	public void setCouponType(Integer couponType) {
		this.couponType = couponType;
	}
	public Double getPar() {
		return par;
	}
	public void setPar(Double par) {
		this.par = par;
	}
	public Double getStartFee() {
		return startFee;
	}
	public void setStartFee(Double startFee) {
		this.startFee = startFee;
	}
	public Double getEndFee() {
		return endFee;
	}
	public void setEndFee(Double endFee) {
		this.endFee = endFee;
	}
	public List<String> getProductIds() {
		return productIds;
	}
	public void setProductIds(List<String> productIds) {
		this.productIds = productIds;
	}
	public String getUserBagId() {
		return userBagId;
	}
	public void setUserBagId(String userBagId) {
		this.userBagId = userBagId;
	}
	public String getCpCode() {
		return cpCode;
	}
	public void setCpCode(String cpCode) {
		this.cpCode = cpCode;
	}
	public String getRichText() {
		return richText;
	}
	public void setRichText(String richText) {
		this.richText = richText;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUrlButton() {
		return urlButton;
	}
	public void setUrlButton(String urlButton) {
		this.urlButton = urlButton;
	}
	public Integer getIsCode() {
		return isCode;
	}
	public void setIsCode(Integer isCode) {
		this.isCode = isCode;
	}
	public Integer getMeanwhile() {
		return meanwhile;
	}
	public void setMeanwhile(Integer meanwhile) {
		this.meanwhile = meanwhile;
	}
	public Integer getMeantime() {
		return meantime;
	}
	public void setMeantime(Integer meantime) {
		this.meantime = meantime;
	}
	@Override
	public String toString() {
		return "UserBagFindEntity [userBagId=" + userBagId + ", cpId=" + cpId + ", cpName=" + cpName + ", beginTime="
				+ beginTime + ", dueTime=" + dueTime + ", type=" + type + ", rule=" + rule + ", memo=" + memo
				+ ", conditionId=" + conditionId + ", couponType=" + couponType + ", par=" + par + ", startFee="
				+ startFee + ", endFee=" + endFee + ", productIds=" + productIds + ", cpCode=" + cpCode + ", richText="
				+ richText + ", url=" + url + ", urlButton=" + urlButton + ", isCode=" + isCode + ", meanwhile="
				+ meanwhile + ", meantime=" + meantime + "]";
	}
	
}
