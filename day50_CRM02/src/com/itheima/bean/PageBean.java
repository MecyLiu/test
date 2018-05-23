package com.itheima.bean;

import java.util.List;

public class PageBean<E> {
	private int currentPage;//当前页
	private int totalPage;//总页数
	private int totalSize;//总数据条数
	private int pageSize;//每页显示数据条数
	private List<E> list;
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public List<E> getList() {
		return list;
	}
	public void setList(List<E> list) {
		this.list = list;
	}
	@Override
	public String toString() {
		return "PageBean [currentPage=" + currentPage + ", totalPage=" + totalPage + ", totalSize=" + totalSize
				+ ", pageSize=" + pageSize + ", list=" + list + "]";
	}
}
