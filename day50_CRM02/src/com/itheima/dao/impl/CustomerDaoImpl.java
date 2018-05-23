package com.itheima.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.itheima.bean.Customer;
import com.itheima.dao.CustomerDao;

public class CustomerDaoImpl extends HibernateDaoSupport implements CustomerDao {

	@Override
	public void save(Customer customer) {
		getHibernateTemplate().save(customer);
	}

	/* 
	 * //criteria 从开始创建到这里都没有任何配置，那么背后生成的sql语句是这样的 ： select * from customer;
		
		//查询总数，需要想方设法编程selct count(*) form customer;
		//离线对象只会有两个方法 ，一个是add  方法  （用来添加查询条件 where），一个是set方法 (用来做聚合查询 ，总记录数 、平均值、 最大值i、最小值 )
	 */
	@Override
	public int getCount(DetachedCriteria criteria) {
		criteria.setProjection(Projections.rowCount());//rowCount()计算一共有多少行
		List<Long> list = (List<Long>) getHibernateTemplate().findByCriteria(criteria);
		if (list.size() > 0) {
			return list.get(0).intValue();//intValue() 将Long类型的数据转换成int型
		}
		return 0;
	}

	@Override
	public List<Customer> findByPage(DetachedCriteria criteria, int currentPage, int pageSize) {
		//在这之前，sql语句是这样的 select count(*) from customer ;
		//将criteria的条件清空
		criteria.setProjection(null);
		int a = (currentPage - 1)*pageSize;//a表示跳过前面多少页
		List<Customer> list = (List<Customer>) getHibernateTemplate().findByCriteria(criteria, a, pageSize);
		return list;
	}

	@Override
	public void delete(Customer customer) {
		getHibernateTemplate().delete(customer);
	}

	@Override
	public Customer findById(Long cust_id) {
		return getHibernateTemplate().get(Customer.class, cust_id);
	}

	@Override
	public void update(Customer customer) {
		getHibernateTemplate().update(customer);
	}

	@Override
	public List<Customer> findAll() {
		return (List<Customer>) getHibernateTemplate().findByCriteria(DetachedCriteria.forClass(Customer.class));
	}

}
