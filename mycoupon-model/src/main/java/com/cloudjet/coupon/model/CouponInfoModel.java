package com.cloudjet.coupon.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CouponInfoModel implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 优惠券类型
	 * */
	public enum CouponInfoType{
		platform,/** 平台优惠券*/
		goods,   /** 商品优惠券 */
		category /** 品类券 */
	}

	/**
	 * 优惠券消费场景类型 0，线上；1线下
	 * */
	public enum CouponInfoCostType{
		online,/** 线上*/
		offline   /** 线下  */
	}

	/**
	 * 优惠券状态 0-初始化。1-已使用。2-删除。3-冻结
	 * */
	public enum CouponInfoStatus{
		/**
		 * 初始化
		 * */
		init,
		/**
		 * 已发送,已使用
		 * */
		send,
		/**
		 * 已删除
		 * */
		del,
		/**
		 * 冻结
		 * */
		freeze

	}

	/**
	 * 优惠券是否是券码优惠券  0 不是券码券，1券码券
	 */
	public enum CouponIsCode{
		/**
		 * 不是券码券
		 */
		no,
		/**
		 * 券码优惠券
		 * */
		yes
	}

	public enum CouponIsBindCode{
		/**
		 * 未绑定
		 * */
		no,
		/**
		 * 已绑定
		 * */
		yes
	}

	public enum CouponIsShare{
		/**
		 * 不是分享券
		 * */
		no,
		/**
		 * 是
		 * */
		yes
	}

	/**
	 * 是否共同使用优惠券。大（商品<品类<平台）0，否。1是
	 * */
	public enum CouponIsMmeanwhileEnum{
		/**
		 * 不可以
		 * */
		no,
		/**
		 * 可以
		 * */
		yes
	}

	/**
	 * 是否共同使用优惠券。小（商品<品类<平台）0，否。1是
	 * */
	public enum CouponIsMmeantimeEnum{
		/**
		 * 不可以
		 * */
		no,
		/**
		 * 可以
		 * */
		yes
	}
	/**
	 * 该券是否能与平台券同时使用
	 * */
	public enum CouponIsMeanwhileEnum{
		/**
		 * 商品券不可以与平台券同时使用，不可以与品类券同时使用 0
		 * */
		productDenyAll,
		/**
		 * 商品券不可以与平台券同时使用，同时可以与品类券同时使用 1
		 * */
		productDenyPlat,
		/**
		 * 商品券可以与平台券同时使用，同时可以与品类券同时使用 2
		 * */
		productSortPlat,
		/**
		 * 商品券可以与平台券同时使用，不可以与品类券同时使用 3
		 * */
		productDenySort,
		/**
		 * 品类券不可以与平台券同时使用，不可以与商品券同事使用 4
		 * */
		SortDenyAll,
		/**
		 * 品类券不可以与平台券同时使用，同时可以与商品券同时使用 5
		 * */
		SortDenyPlat,
		/**
		 * 品类券可以与平台券同时使用，同时可以与商品券同时使用 6
		 * */
		SortPlatProduct,
		/**
		 * 品类券可以与平台券同时使用，同时不可以与商品券同时使用 7
		 * */
		SortDenyProduct,
		/**
		 * 平台券不可以与商品券同时使用，同时不可以与品类券同时使用 8
		 * */
		PlatDenyAll,
		/**
		 * 平台券不可以与商品券同时使用，同时可以与品类券同时使用 9
		 * */
		PlatDenyProduct,
		/**
		 * 平台券可以与商品券同时使用，同时可以与品类券同时使用 10
		 * */
		PlatProductSort,
		/**
		 * 平台券可以与商品券同时使用，同时不可以与品类券同时使用 10
		 * */
		PlatDenySort

	}

	/**
	 * 优惠券商铺信息
	 */
	private List<CouponShopInfoModel> couponShopInfoModels;

	/**
	 * 优惠券条件，条件参数
	 * */
	private InfoConditionModel infoCondition;

	/**
	 * 优惠券 操作者 创建人
	 * */
	private InfoOperatorModel operator;

	/**
	 * 优惠券 发放记录
	 * */
	private List<InfoSendRecordModel> infoSendRecordModels;

	/**
	 * 优惠券使用范围
	 * */
	private InfoScopeModel infoScope;

	/**
	 * 优惠券渠道
	 * */
	private List<ChannelModel> channels;

	/**
	 * 优惠券logo
	 */
	private List<LogoModel> logos;

	/**
	 * 优惠券Id
	 * */
	private String cpId;

	/**
	 * 优惠券名称
	 * */
	private String cpName;

	/**
	 * 先决条件，针对什么用户进行发放
	 * */
	private String preWay;

	/**
	 * 优惠券校验码
	 */
	private String checkNo;

	/**
	 * 券领取开始时间
	 * */
	private Date startTime;

	/**
	 * 券领取结束时间
	 * */
	private Date endTime;

	/**
	 * 发行量
	 * */
	private Integer circulation;

	/**
	 * 每人限制领取的张数
	 * */
	private Integer limitNum;

	/**
	 * 库存，与发行量保持一致
	 */
	private Integer stock;

	/**
	 * 券使用开始时间
	 * */
	private Date beginTime;

	/**
	 * 券使用过期时间
	 * */
	private Date dueTime;

	/**
	 * 优惠券类型
	 * 0,平台优惠券；1商品优惠券 2 分类券
	 * */
	private Integer type;

	/**
	 * 优惠券状态  0,初始化 1,已使用 2,删除 3.冻结
	 * */
	private Integer status;

	/**
	 * 是否是券码优惠券  0,不是； 1,是
	 * */
	private Integer isCode;

	/**
	 * 券码是否绑定优惠券  0,不是； 1,是
	 * */
	private Integer isBindCode;

	/**
	 * 优惠券消费类型
	 * 0,线上；1线下
	 * */
	private Integer costType;
	/**
	 * 是否是分享券 0,不是；1，是
	 * */
	private Integer isShare;

	/**
	 * 优惠券备注
	 * */
	private String memo;

	/**
	 * 优惠券使用规则
	 */
	private String rule;

	/**
	 * 预约URL
	 * */
	private String url;
	/**
	 * 预约URL按钮
	 * */
	private String urlButton;

	/**
	 *富文本
	 * */
	private String richText;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 更新时间
	 */
	private Date updateTime;

	/**
	 * 已经发放数目
	 */
	private Integer grantNum;

	/**
	 * 已经使用数目
	 */
	private Integer usedNum;

	/**
	 * 用户类型
	 */
	private Integer userSourceType;

	/**
	 * 分享用户手机号-券(包)id-0(券)1(包)-分享时间戳
	 */
	private String shareCode;

	/**
	 *
	 * 是否共同使用优惠券。大
	 *
	 * */
	private Integer meanwhile;

	/**
	 *
	 * 是否共同使用优惠券。小
	 *
	 * */
	private Integer meantime;

	/**
	 * 券码优惠券的券码类型，0 通用，1 链接
	 */
	private Integer codeType;

	/**
	 * 渠道描述
	 */
	private String channelText;
	
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

	public String getShareCode() {
		return shareCode;
	}

	public void setShareCode(String shareCode) {
		this.shareCode = shareCode;
	}

	public Integer getUserSourceType() {
		return userSourceType;
	}

	public void setUserSourceType(Integer userSourceType) {
		this.userSourceType = userSourceType;
	}

	public Integer getGrantNum() {
		return grantNum;
	}

	public void setGrantNum(Integer grantNum) {
		this.grantNum = grantNum;
	}

	public Integer getUsedNum() {
		return usedNum;
	}

	public void setUsedNum(Integer usedNum) {
		this.usedNum = usedNum;
	}

	public InfoConditionModel getInfoCondition() {
		return infoCondition;
	}

	public void setInfoCondition(InfoConditionModel infoCondition) {
		this.infoCondition = infoCondition;
	}

	public InfoOperatorModel getOperator() {
		return operator;
	}

	public void setOperator(InfoOperatorModel operator) {
		this.operator = operator;
	}

	public List<InfoSendRecordModel> getInfoSendRecordModels() {
		return infoSendRecordModels;
	}

	public void setInfoSendRecordModels(List<InfoSendRecordModel> infoSendRecordModels) {
		this.infoSendRecordModels = infoSendRecordModels;
	}

	public InfoScopeModel getInfoScope() {
		return infoScope;
	}

	public void setInfoScope(InfoScopeModel infoScope) {
		this.infoScope = infoScope;
	}

	public List<ChannelModel> getChannels() {
		return channels;
	}

	public void setChannels(List<ChannelModel> channels) {
		this.channels = channels;
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

	public Integer getLimitNum() {
		return limitNum;
	}

	public void setLimitNum(Integer limitNum) {
		this.limitNum = limitNum;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
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

	public Integer getIsCode() {
		return isCode;
	}

	public void setIsCode(Integer isCode) {
		this.isCode = isCode;
	}

	public Integer getCostType() {
		return costType;
	}

	public void setCostType(Integer costType) {
		this.costType = costType;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getIsBindCode() {
		return isBindCode;
	}

	public void setIsBindCode(Integer isBindCode) {
		this.isBindCode = isBindCode;
	}

	public List<CouponShopInfoModel> getCouponShopInfoModels() {
		return couponShopInfoModels;
	}

	public void setCouponShopInfoModels(List<CouponShopInfoModel> couponShopInfoModels) {
		this.couponShopInfoModels = couponShopInfoModels;
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

	public List<LogoModel> getLogos() {
		return logos;
	}

	public void setLogos(List<LogoModel> logos) {
		this.logos = logos;
	}


	public Integer getMeantime() {
		return meantime;
	}

	public void setMeantime(Integer meantime) {
		this.meantime = meantime;
	}
	@Override
	public String toString() {
		return "CouponInfoModel [couponShopInfoModels=" + couponShopInfoModels + ", infoCondition=" + infoCondition
				+ ", operator=" + operator + ", infoSendRecordModels=" + infoSendRecordModels + ", infoScope="
				+ infoScope + ", channels=" + channels + ", logos=" + logos + ", cpId=" + cpId + ", cpName=" + cpName
				+ ", preWay=" + preWay + ", checkNo=" + checkNo + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", circulation=" + circulation + ", limitNum=" + limitNum + ", stock=" + stock + ", beginTime="
				+ beginTime + ", dueTime=" + dueTime + ", type=" + type + ", status=" + status + ", isCode=" + isCode
				+ ", isBindCode=" + isBindCode + ", costType=" + costType + ", isShare=" + isShare + ", memo=" + memo
				+ ", rule=" + rule + ", url=" + url + ", urlButton=" + urlButton + ", richText=" + richText
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + ", grantNum=" + grantNum + ", usedNum="
				+ usedNum + ", userSourceType=" + userSourceType + ", shareCode=" + shareCode + ", meanwhile="
				+ meanwhile + ", meantime=" + meantime + ", codeType=" + codeType + ", channelText=" + channelText
				+ "]";
	}

	

}
