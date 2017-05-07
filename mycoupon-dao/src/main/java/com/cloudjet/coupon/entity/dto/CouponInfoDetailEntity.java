package com.cloudjet.coupon.entity.dto;

import java.io.Serializable;
import java.util.Date;

public class CouponInfoDetailEntity implements Serializable{

	/**
	 * 优惠券详情表
	 */
	private static final long serialVersionUID = 1L;
	private String cpId;//优惠券Id
	private String cpName;//优惠券名字
	private Integer type;//0,平台优惠券；1商品优惠券
	private String preWay;//先决条件，针对什么用户进行发放
	private String checkNo;//优惠券校验码
	private Date beginTime;//优惠券开始使用时间
	private Date dueTime;//优惠券截止使用时间
	private String conditionType;//优惠券类型 zero，无门槛；reduce，满减 ；discount，折扣券
	private Double par;//优惠券面额、折扣
	private Double startFee;//区间金额开始
	private Double endFee;//区间金额结束
	private Integer stock;//库存
	private Integer limitNum;//每人限制领多少张
	private String rule;//优惠券使用规则
	private String memo;//备注
	private Date startTime;//优惠券领取开始时间
	private Date endTime;//优惠券领取结束时间
	private Integer status;//券的发布状态
	private Date createTime;//创建时间
	private Date updateTime;//创建时间
	private Integer circulation;//发行量
	private Integer userSourceType;//用户来源 0，等级；1，用户导入；2，补发用户,3券码用户
	private String operatorName;//券的操作人姓名
	private String richText;//富文本
	private String url;
	private String urlButton;
	private Integer costType; //线上线写券 0，线上；1，线下
	private Integer isBindCode;//是否绑定券码
	private Integer isCode;//是否是券码优惠券
	private Integer isShare;//是否是分享券0，不是；1是
	private Integer meanwhile;//是否共同使用优惠券。大（商品<品类<平台））0，否。1是
	private Integer meantime;//是否共同使用优惠券。小（商品<品类<平台））0，否。1是
	private Integer codeType;//券码类型 0 普通 ， 1链接
	private String channelText;//渠道描述
	
	public String getChannelText() {
		return channelText;
	}
	public void setChannelText(String channelText) {
		this.channelText = channelText;
	}
	
	public Integer getCodeType() {
		return codeType;
	}
	public void setCodeType(Integer codeType) {
		this.codeType = codeType;
	}
	public Integer getIsBindCode() {
		return isBindCode;
	}
	public void setIsBindCode(Integer isBindCode) {
		this.isBindCode = isBindCode;
	}
	public Integer getIsCode() {
		return isCode;
	}
	public void setIsCode(Integer isCode) {
		this.isCode = isCode;
	}
	public String getRichText() {
		return richText;
	}
	public void setRichText(String richText) {
		this.richText = richText;
	}
	public String getCpId() {
		return cpId;
	}
	public void setCpId(String cpId) {
		this.cpId = cpId;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getCpName() {
		return cpName;
	}
	public void setCpName(String cpName) {
		this.cpName = cpName;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getCirculation() {
		return circulation;
	}
	public void setCirculation(Integer circulation) {
		this.circulation = circulation;
	}
	public Integer getUserSourceType() {
		return userSourceType;
	}
	public void setUserSourceType(Integer userSourceType) {
		this.userSourceType = userSourceType;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
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
	public Integer getCostType() {
		return costType;
	}
	public void setCostType(Integer costType) {
		this.costType = costType;
	}
	public Integer getIsShare() {
		return isShare;
	}
	public void setIsShare(Integer isShare) {
		this.isShare = isShare;
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
	
	
}
