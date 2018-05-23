package com.itheima.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bean.LinkMan;
import com.itheima.bean.PageBean;
import com.itheima.dao.LinkManDao;
import com.itheima.service.LinkManService;
@Transactional
public class LinkManServiceImpl implements LinkManService{

	private LinkManDao linkMandao;
	public void setLinkMandao(LinkManDao linkMandao) {
		this.linkMandao = linkMandao;
	}
	
	@Override
	public void save(LinkMan linkMan) {
		linkMandao.save(linkMan);
	}

	@Override
	public PageBean<LinkMan> findByPage(DetachedCriteria criteria, int currentPage, int pageSize) {
		PageBean<LinkMan> pageBean = new PageBean<>();
		
		pageBean.setCurrentPage(currentPage);
		pageBean.setPageSize(pageSize);
		int totalSize = linkMandao.getCount(criteria);
		pageBean.setTotalSize(totalSize);
		int totalPage = (int) Math.ceil(totalSize * 1.0 / pageSize);
		pageBean.setTotalPage(totalPage);
		List<LinkMan> list = linkMandao.findByPage(criteria,currentPage,pageSize);
		pageBean.setList(list);
		
		return pageBean;
	}

	@Override
	public void delete(LinkMan linkMan) {
		linkMandao.delete(linkMan);
	}

	@Override
	public LinkMan findById(Long lkm_id) {
		return linkMandao.findById(lkm_id);
	}

	@Override
	public void update(LinkMan linkMan) {
		linkMandao.update(linkMan);
	}

	@Override
	public List<LinkMan> findByCid(String cid) {
		return linkMandao.findByCid(cid);
	}

}
