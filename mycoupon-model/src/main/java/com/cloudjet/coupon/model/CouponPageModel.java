package com.cloudjet.coupon.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 优惠券列表项Model
 *
 */
public class CouponPageModel implements Serializable {

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
	 * 优惠券Name
	 */
	private String cpName;
	
	/**
	 * 优惠券类型
	 * 0,平台优惠券；1商品优惠券
	 */
	private Integer type;
		
	/**
	 * 创建时间
	 */
	private Date createTime_left;
	private Date createTime_right;
	
	/**
	 * 有效时间
	 */
	private Date beginTime;
	private Date dueTime;
	
	/**
	 * 优惠券状态
	 */
	private Integer status;

	/**
	 * 面额
	 */
	private Double par;
	
	/**
	 * 页码。取值范围:大于零的整数;
	 */
	private Integer pageNo;
	
	/**
	 * 每页条数。取值范围:大于零的整数;
	 */
	private Integer pageSize;
	
	/**
	 * 店铺code
	 * */
	private String platCode;

	/**
	 * 优惠券消费类型
	 * 0,线上；1线下
	 * */
	private Integer costType;
	
	public Integer getCostType() {
		return costType;
	}

	public void setCostType(Integer costType) {
		this.costType = costType;
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

	public Date getCreateTime_left() {
		return createTime_left;
	}

	public void setCreateTime_left(Date createTime_left) {
		this.createTime_left = createTime_left;
	}

	public Date getCreateTime_right() {
		return createTime_right;
	}

	public void setCreateTime_right(Date createTime_right) {
		this.createTime_right = createTime_right;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Double getPar() {
		return par;
	}

	public void setPar(Double par) {
		this.par = par;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getPlatCode() {
		return platCode;
	}

	public void setPlatCode(String platCode) {
		this.platCode = platCode;
	}

	@Override
	public String toString() {
		return "CouponPageModel [cpName=" + cpName + ", type=" + type + ", createTime_left=" + createTime_left
				+ ", createTime_right=" + createTime_right + ", beginTime=" + beginTime + ", dueTime=" + dueTime
				+ ", status=" + status + ", par=" + par + ", pageNo=" + pageNo + ", pageSize=" + pageSize
				+ ", platCode=" + platCode + ", costType=" + costType + "]";
	} 
	
	
	
	
}
