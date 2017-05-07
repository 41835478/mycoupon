package com.cloudjet.coupon.entity;

public class ProductSortLogEntity extends BaseEntity{

	/**
	 * 分类记录
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 商铺号
	 */
	private String shopCode;
	
	/**
	 * 分类id
	 */
	private String sortId;
	
	/**
	 * 分类名
	 */
	private String name;
	
	/**
	 * 商品id 
	 */
	private String productId;
	
	
	/**
	 * 操作类型，0 创建 ，1编辑 ，2删除
	 */
	public Integer type;
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getSortId() {
		return sortId;
	}

	public void setSortId(String sortId) {
		this.sortId = sortId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "ProductSortEntity [shopCode=" + shopCode + ", name=" + name + "]";
	}
	
}
