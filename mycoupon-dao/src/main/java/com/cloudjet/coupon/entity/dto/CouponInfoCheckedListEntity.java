package com.cloudjet.coupon.entity.dto;

import java.util.Date;

import com.cloudjet.coupon.entity.BaseEntity;

public class CouponInfoCheckedListEntity extends BaseEntity {

	/**
	 * 查询未发布：优惠券主表信息+优惠券条件
	 */
	private static final long serialVersionUID = 1L;
	
	private String cpName;//优惠券名字
	private String preWay;//先决条件，针对什么用户进行发放
	private String checkNo;//优惠券校验码
	private Date startTime;//优惠券领取开始时间
	private Date endTime;//优惠券领取结束时间
	private Integer circulation;//发行量
	private Integer stock;//库存
	private Integer limitNum;//每人限制领多少张
	private Date beginTime;//优惠券开始使用时间
	private Date dueTime;//优惠券截止使用时间
	private Integer type;//0,平台优惠券；1商品优惠券
	private Integer status;//0,初始化 1,已使用 2,删除 3.冻结
	private Integer userSourceType;//用户来源 0，等级；1，用户导入；2，补发用户
	private Integer costType;//消费场景类型：0，线上；1线下
	private Integer isCode;//是否是券码券,0 不是，1是
	private Integer isBindCode;//是否绑定code，0 不是，1是
	private String memo;//备注
	private String rule;//优惠券使用规则
	private String url; //配置URL
	private String urlButton; //配置URL
	private String richText;//富文本
	private String conditionType;//优惠券类型   zero,无门槛；reduce,满减；discount,折扣券；
	private Double par;//面值 折扣
	private Double startFee;//区间金额开始
	private Double endFee; //区间金额 结束
	private Integer isShare;//是否是分享券0，不是；1是
	
	public Integer getIsShare() {
		return isShare;
	}
	public void setIsShare(Integer isShare) {
		this.isShare = isShare;
	}
	public Integer getCostType() {
		return costType;
	}
	public void setCostType(Integer costType) {
		this.costType = costType;
	}
	public Integer getIsCode() {
		return isCode;
	}
	public void setIsCode(Integer isCode) {
		this.isCode = isCode;
	}
	public Integer getIsBindCode() {
		return isBindCode;
	}
	public void setIsBindCode(Integer isBindCode) {
		this.isBindCode = isBindCode;
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
	public String getRichText() {
		return richText;
	}
	public void setRichText(String richText) {
		this.richText = richText;
	}
	public String getCpName() {
		return cpName;
	}
	public void setCpName(String cpName) {
		this.cpName = cpName;
	}

	public String getPreWay() {
		return preWay;
	}
	public void setPreWay(String preWay) {
		this.preWay = preWay;
	}
	public String getCheckNo() {
		return checkNo;
	}
	public void setCheckNo(String checkNo) {
		this.checkNo = checkNo;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Integer getCirculation() {
		return circulation;
	}
	public void setCirculation(Integer circulation) {
		this.circulation = circulation;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public Integer getLimitNum() {
		return limitNum;
	}
	public void setLimitNum(Integer limitNum) {
		this.limitNum = limitNum;
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
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getUserSourceType() {
		return userSourceType;
	}
	public void setUserSourceType(Integer userSourceType) {
		this.userSourceType = userSourceType;
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
	public String getConditionType() {
		return conditionType;
	}
	public void setConditionType(String conditionType) {
		this.conditionType = conditionType;
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
	
	
	
	
}
