package com.itheima.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.itheima.bean.Customer;
import com.itheima.dao.CustomerDao;
@Repository
public class CustomerDaoImpl extends HibernateDaoSupport implements CustomerDao {
	
	@Autowired
	private void SetSessionFactory(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	@Override
	public void save(Customer customer) {
		getHibernateTemplate().save(customer);
	}

}
