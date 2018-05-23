package com.itheima.web.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.annotations.JSON;
import org.jboss.logging.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.itheima.bean.BaseDict;
import com.itheima.service.BaseDictService;
import com.opensymphony.xwork2.ActionSupport;
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("json-default")
@Result(name="json_success",type="json")
public class BaseDictAction extends ActionSupport {
	private String type;//数据字典类别代码
	public void setType(String type) {
		this.type = type;
	}
	private List<BaseDict> list;
	public List<BaseDict> getList() {
		return list;
	}
	@Autowired
	private BaseDictService baseDictService;
	@Action(value="baseDict_findByType",results={@Result(type="json",params={"root","list" })})
	public String findByType() throws IOException {
		//按照类型去查询字典数据，一种类型可能有多个  客户级别（普通客户 & vip客户）
		list = baseDictService.findByType(type);
		return NONE;
	}
}
