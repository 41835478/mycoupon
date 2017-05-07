package com.cloudjet.coupon.util;

import java.io.Serializable;
import java.util.List;

public class PageResult<T> implements Serializable{
	
	/**
	 * 列表查询结果
	 */
	private static final long serialVersionUID = 1L;
	private int totalResults;// 搜索到的信息总数
	private boolean hasNext; //是否存在下一页
	
	private List<T> data;

	public int getTotalResults() {
		return totalResults;
	}

	public void setTotalResults(int totalResults) {
		this.totalResults = totalResults;
	}

	public boolean isHasNext() {
		return hasNext;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}
	

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "PageResult [totalResults=" + totalResults + ", data=" + data + "]";
	}

	
	
	
	
	
}
