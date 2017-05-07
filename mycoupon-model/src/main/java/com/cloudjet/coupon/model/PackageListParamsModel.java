package com.cloudjet.coupon.model;

import java.io.Serializable;
import java.util.Date;

public class PackageListParamsModel implements Serializable{

	/**
	 * 券包列表查询
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 券包名字
	 */
	private String name;

	/**
	 * 券包状态 0，初始化；1，绑定优惠券；2，冻结
	 */
	private Integer status;

	/**
	 * 券包领取时间
	 */
	private Date startTime;

	/**
	 * 券包领取结束时间
	 */
	private Date endTime;

	/**
	 * 页码。取值范围:大于零的整数;
	 */
	private Integer pageNo;

	/**
	 * 每页条数。取值范围:大于零的整数;
	 */
	private Integer pageSize;

	/**
	 * 是否分页
	 */
	private boolean ispage;

	/**
	 * 商铺号
	 */
	private String platCode;
	
	public String getPlatCode() {
		return platCode;
	}
	public void setPlatCode(String platCode) {
		this.platCode = platCode;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	public boolean isIspage() {
		return ispage;
	}
	public void setIspage(boolean ispage) {
		this.ispage = ispage;
	}
	@Override
	public String toString() {
		return "PackageListParamsModel [name=" + name + ", status=" + status + ", startTime=" + startTime + ", endTime="
				+ endTime + ", pageNo=" + pageNo + ", pageSize=" + pageSize + ", ispage=" + ispage + ", platCode="
				+ platCode + "]";
	}
	
	
}
