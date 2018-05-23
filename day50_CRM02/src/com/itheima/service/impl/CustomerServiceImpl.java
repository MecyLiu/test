package com.itheima.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bean.Customer;
import com.itheima.bean.PageBean;
import com.itheima.dao.CustomerDao;
import com.itheima.service.CustomerService;
@Transactional
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerDao customerDao;
	public void setCustomerdao(CustomerDao customerdao) {
		this.customerDao = customerdao;
	}
	@Override
	public void add(Customer customer) {
		customerDao.save(customer);
	}
	/* 
	 * dao查到的数据是和数据库表对应的,因此这里我们需要pageBean就需要自己手动封装进去
	 */
	@Override
	public PageBean<Customer> findByPage(DetachedCriteria criteria, int currentPage, int pageSize) {
		PageBean<Customer> pageBean = new PageBean<Customer>();
		//1.封装当前页
		pageBean.setCurrentPage(currentPage);
		//2.封装每页数据条数
		pageBean.setPageSize(pageSize);
		//3.封装总数据条数
		int totalSize = customerDao.getCount(criteria);
		pageBean.setTotalSize(totalSize);
		//4.封装总页数
		//ceil : 向上取整  3.2  3.1  3.9  === 4 
		int totalPage = (int) Math.ceil(totalSize * 1.0 / pageSize);
		pageBean.setTotalPage(totalPage);
		//5.封装customer对象的list集合
		List<Customer> list = customerDao.findByPage(criteria,currentPage,pageSize);
		pageBean.setList(list);
		
		return pageBean;
	}
	@Override
	public void delete(Customer customer) {
		customerDao.delete(customer);
	}
	@Override
	public Customer findById(Long cust_id) {
		Customer customer = customerDao.findById(cust_id);
		return customer;
	}
	@Override
	public void update(Customer customer) {
		customerDao.update(customer);
	}
	@Override
	public List<Customer> findAll() {
		return customerDao.findAll();
	}

}
