package com.itheima.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bean.LinkMan;
import com.itheima.bean.PageBean;
import com.itheima.bean.SaleVisit;
import com.itheima.dao.SaleVisitDao;
import com.itheima.service.SaleVisitService;
@Transactional
public class SaleVisitServiceImpl implements SaleVisitService {
	private SaleVisitDao saleVisitDao;
	public void setSaleVisitDao(SaleVisitDao saleVisitDao) {
		this.saleVisitDao = saleVisitDao;
	}
	@Override
	public void add(SaleVisit saleVisit) {
		saleVisitDao.save(saleVisit);
	}
	@Override
	public PageBean<SaleVisit> findByPage(DetachedCriteria criteria, int currentPage, int pageSize) {
		PageBean<SaleVisit> pageBean = new PageBean<>();
		
		pageBean.setCurrentPage(currentPage);
		pageBean.setPageSize(pageSize);
		int totalSize = saleVisitDao.getCount(criteria);
		pageBean.setTotalSize(totalSize);
		int totalPage = (int) Math.ceil(totalSize * 1.0 / pageSize);
		pageBean.setTotalPage(totalPage);
		List<SaleVisit> list = saleVisitDao.findByPage(criteria,currentPage,pageSize);
		pageBean.setList(list);
		
		return pageBean;
	}

}
