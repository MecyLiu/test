package com.itheima.web.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bean.Customer;
import com.itheima.service.CustomerService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
public class CustomerAction extends ActionSupport implements ModelDriven<Customer>{
	private Customer customer;
	@Override
	public Customer getModel() {
		if (customer == null) {
			customer = new Customer();
		}
		return customer;
	}
	@Autowired
	private CustomerService customerService;
	
	/**
	 * 添加客户的方法
	 * @return
	 */
	@Action(value="customer_add")
	public String add() {
		customerService.add(customer);
		return NONE;
	}
}
