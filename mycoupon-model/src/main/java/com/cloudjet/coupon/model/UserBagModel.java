package com.cloudjet.coupon.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class UserBagModel implements Serializable{

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
	 * 
	 * */
	public enum UserBagBestUsed{
		no,
		best
	}
	
	/**
	 * 0，全场券不是最优；1全场券最优
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String userBagId;//优惠券包id
	/**
	 * 优惠券id
	 * */
	private String cpId;
	private String cpName;//优惠劵名字
	private Date beginTime;//优惠劵使用日期
	private Date dueTime;//优惠劵使用日期
	private String type;//优惠券类型 zero，无门槛；reduce，满减 ；discount，折扣券
	private Double par;//优惠劵金额
	private Double startFee;//优惠劵使用范围
	private Double endFee;//优惠劵使用范围
	private String memo;//优惠券备注
	private String rule;//优惠券使用规则说明
	private String tel;//电话号码
	private Integer status;// 用户券包状态，0,已领取,被发放  1,已消费  2,已删除
	private Integer couponType;// 0,平台优惠券;1商品优惠券
	private Integer bestUsed;//0，全场券不是最优；1全场券最优
	private Integer isCode;//是否是券码券
	private String cpCode;
	private String richText;
	private String url;
	private String urlButton;
	/**
	 * 
	 * 是否与平台券同时使用0，否；1,是 大范围
	 * 
	 * */
	private Integer meanwhile;//是否能与平台券同时使用 0,否，1是 
	/**
	 *是否与券同时使用   0否 1是   小范围
	 * */
	private Integer meantime;
	/**
	 * 当优惠券是商品优惠券包括的商品列表(品类券包含的商品id)
	 * */
	private List<String> productIds;
	
	/**
	 * 最终使用的商品id
	 * */
	private String productId;
	
	/**
	 * 分类id
	 * */
	private String sortId;
	
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getCouponType() {
		return couponType;
	}

	public void setCouponType(Integer couponType) {
		this.couponType = couponType;
	}

	public Integer getBestUsed() {
		return bestUsed;
	}

	public void setBestUsed(Integer bestUsed) {
		this.bestUsed = bestUsed;
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
	
	public List<String> getProductIds() {
		return productIds;
	}

	public void setProductIds(List<String> productIds) {
		this.productIds = productIds;
	}

	
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	public String getSortId() {
		return sortId;
	}

	public void setSortId(String sortId) {
		this.sortId = sortId;
	}

	@Override
	public String toString() {
		return "UserBagModel [userBagId=" + userBagId + ", cpId=" + cpId + ", cpName=" + cpName + ", beginTime="
				+ beginTime + ", dueTime=" + dueTime + ", type=" + type + ", par=" + par + ", startFee=" + startFee
				+ ", endFee=" + endFee + ", memo=" + memo + ", rule=" + rule + ", tel=" + tel + ", status=" + status
				+ ", couponType=" + couponType + ", bestUsed=" + bestUsed + ", isCode=" + isCode + ", cpCode=" + cpCode
				+ ", richText=" + richText + ", url=" + url + ", urlButton=" + urlButton + ", meanwhile=" + meanwhile
				+ ", meantime=" + meantime + ", productIds=" + productIds + ", productId=" + productId + "]";
	}
	
		
}
