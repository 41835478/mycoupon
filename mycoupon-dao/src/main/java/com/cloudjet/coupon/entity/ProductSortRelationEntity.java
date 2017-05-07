package com.cloudjet.coupon.entity;

public class ProductSortRelationEntity extends BaseEntity{

	/**
	 * 商品分类明细
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 商品id
	 */
	private String productId;
	
	/**
	 * 分类id
	 */
	private String sortId;

	/**
	 * 操作类型 
	 */
	private Integer type;
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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
		return "ProductSortRelationEntity [productId=" + productId + ", sortId=" + sortId + ", type=" + type + "]";
	}

	
	
}
