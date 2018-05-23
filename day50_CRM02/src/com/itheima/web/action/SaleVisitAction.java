package com.itheima.web.action;

import java.util.Date;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.StringUtils;

import com.itheima.bean.PageBean;
import com.itheima.bean.SaleVisit;
import com.itheima.bean.User;
import com.itheima.constant.Constant;
import com.itheima.service.SaleVisitService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class SaleVisitAction extends ActionSupport implements ModelDriven<SaleVisit>{
	private int currentPage = 1;
	private int pageSize = 5;
	private Date start_visit_time;
	private Date end_visit_time;
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public void setStart_visit_time(Date start_visit_time) {
		this.start_visit_time = start_visit_time;
	}
	public void setEnd_visit_time(Date end_visit_time) {
		this.end_visit_time = end_visit_time;
	}
	private SaleVisit saleVisit;
	public void setSaleVisit(SaleVisit saleVisit) {
		this.saleVisit = saleVisit;
	}
	private SaleVisitService saleVisitService;
	public void setSaleVisitService(SaleVisitService saleVisitService) {
		this.saleVisitService = saleVisitService;
	}
	@Override
	public SaleVisit getModel() {
		if (saleVisit == null) {
			saleVisit = new SaleVisit();
		}
		return saleVisit;
	}
	
	public String add() {
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		saleVisit.setUser(user);
		saleVisitService.add(saleVisit);
		return Constant.SAVE_SUCCESS;
	}
	
	
	public String findByPage() {
		//校验:如果开始时间大于结束时间,拒绝服务,直接报错
		if (start_visit_time != null && end_visit_time != null && start_visit_time.after(end_visit_time)) {
			addActionError("起始时间不能大于结束时间!");
			return Constant.INPUT_ERROR;
		}
		
		DetachedCriteria criteria = DetachedCriteria.forClass(SaleVisit.class);
		
		//校验客户名称
		if (saleVisit.getCustomer()!=null && !StringUtils.isEmpty(saleVisit.getCustomer().getCust_id())){
			criteria.add(Restrictions.eq("customer.cust_id", saleVisit.getCustomer().getCust_id()));
		}
		
		//起始时间和结束时间
		if (start_visit_time != null && end_visit_time != null) {
			criteria.add(Restrictions.between("visit_time", "start_visit_time", "end_visit_time"));
		}else {
			//有可能都没有,或者只有一个
			if (start_visit_time != null) {
				criteria.add(Restrictions.ge("visit_time", start_visit_time));
			}
			if (end_visit_time != null) {
				criteria.add(Restrictions.le("visit_time", end_visit_time));
			}
		}
		
		PageBean<SaleVisit> pageBean = saleVisitService.findByPage(criteria,currentPage,pageSize);
		ActionContext.getContext().getValueStack().push(pageBean);
		return Constant.PAGE_SUCCESS;
	}
	
}
