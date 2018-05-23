package com.itheima.web.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.itheima.bean.BaseDict;
import com.itheima.constant.Constant;
import com.itheima.service.BaseDictService;
import com.opensymphony.xwork2.ActionSupport;

public class BaseDictAction extends ActionSupport {
	private List<BaseDict> list;
	//因为一会struts框架要来调用这个方法，获取到list之后，把它变成json，然后传输给页面
	public List<BaseDict> getList() {
		return list;
	}
	
	private String type;//数据字典类别代码
	public void setType(String type) {
		this.type = type;
	}
	private BaseDictService baseDictService;
	public void setBaseDictService(BaseDictService baseDictService) {
		this.baseDictService = baseDictService;
	}
	public String findByType() throws IOException {
		//按照类型去查询字典数据，一种类型可能有多个  客户级别（普通客户 & vip客户）
		list = baseDictService.findByType(type);
		return Constant.JSON_SUCCESS;
	}
}
