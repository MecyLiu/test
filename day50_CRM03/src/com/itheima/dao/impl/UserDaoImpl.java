package com.itheima.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.itheima.bean.User;
import com.itheima.dao.UserDao;
@Repository
public class UserDaoImpl extends HibernateDaoSupport implements UserDao {
	
	@Autowired
	private void SetSessionFactory(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	
	@Override
	public void save(User user) {
		getHibernateTemplate().save(user);
	}

	@Override
	public User findUser(User user) {
		String hql = "from User where user_code = ? and user_password = ?";
		List<User> list = (List<User>) getHibernateTemplate().find(hql, user.getUser_code(),user.getUser_password());
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

}
