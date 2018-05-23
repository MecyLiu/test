package com.itheima.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itheima.bean.BaseDict;
import com.itheima.dao.BaseDictDao;
import com.itheima.service.BaseDictService;
//@Transactional//查询可以不用添加事务
@Service
public class BaseDictServiceImpl implements BaseDictService {
	@Autowired
	private BaseDictDao baseDictDao;
	
	@Override
	public List<BaseDict> findByType(String dict_type_code) {
		List<BaseDict> list = baseDictDao.findByType(dict_type_code);
		return list;
	}

}
