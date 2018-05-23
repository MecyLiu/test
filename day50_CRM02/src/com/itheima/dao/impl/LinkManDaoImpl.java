package com.itheima.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.itheima.bean.LinkMan;
import com.itheima.dao.LinkManDao;

public class LinkManDaoImpl extends HibernateDaoSupport implements LinkManDao {
	
	@Override
	public void save(LinkMan linkMan) {
		getHibernateTemplate().save(linkMan);
	}

	@Override
	public int getCount(DetachedCriteria criteria) {
		criteria.setProjection(Projections.rowCount());
		List<Long> list = (List<Long>) getHibernateTemplate().findByCriteria(criteria);
		if (list.size() > 0) {
			return list.get(0).intValue();
		}
		return 0;
	}

	@Override
	public List<LinkMan> findByPage(DetachedCriteria criteria, int currentPage, int pageSize) {
		criteria.setProjection(null);
		return (List<LinkMan>) getHibernateTemplate().findByCriteria(criteria, (currentPage - 1)*pageSize, pageSize);
	}

	@Override
	public void delete(LinkMan linkMan) {
		getHibernateTemplate().delete(linkMan);
	}

	@Override
	public LinkMan findById(Long lkm_id) {
		return getHibernateTemplate().get(LinkMan.class, lkm_id);
	}

	@Override
	public void update(LinkMan linkMan) {
		getHibernateTemplate().update(linkMan);
	}

	@Override
	public List<LinkMan> findByCid(String cid) {
		DetachedCriteria criteria = DetachedCriteria.forClass(LinkMan.class);
		criteria.add(Restrictions.eq("customer.cust_id", Long.parseLong(cid)));
		return (List<LinkMan>) getHibernateTemplate().findByCriteria(criteria);
	}

}
