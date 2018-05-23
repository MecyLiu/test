package com.itheima.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bean.Customer;
import com.itheima.dao.CustomerDao;
import com.itheima.service.CustomerService;
@Transactional
@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerDao customerdao;
	@Override
	public void add(Customer customer) {
		customerdao.save(customer);
	}

}
