package com.itheima.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.itheima.bean.BaseDict;
import com.itheima.dao.BaseDictDao;
@Repository
public class BaseDictDaoImpl extends HibernateDaoSupport implements BaseDictDao {

	@Autowired
	private void SetSessionFactory(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	@Override
	public List<BaseDict> findByType(String dict_type_code) {
		String hql = "from BaseDict where dict_type_code = ?";
		List<BaseDict> list = (List<BaseDict>) getHibernateTemplate().find(hql, dict_type_code);
		return list;
	}

}
