package com.itheima.web.action;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.StringUtils;

import com.itheima.bean.LinkMan;
import com.itheima.bean.PageBean;
import com.itheima.constant.Constant;
import com.itheima.service.LinkManService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class LinkManAction extends ActionSupport implements ModelDriven<LinkMan>{
	private String cid;
	public void setCid(String cid) {
		this.cid = cid;
	}
	private List<LinkMan> list;
	public List<LinkMan> getList() {
		return list;
	}
	private LinkMan editLinkMan;
	public LinkMan getEditLinkMan() {
		return editLinkMan;
	}
	
	private int currentPage = 1;
	private int pageSize = 5;
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	private LinkMan linkMan;
	private LinkManService linkManService;
	public void setLinkMan(LinkMan linkMan) {
		this.linkMan = linkMan;
	}
	public void setLinkManService(LinkManService linkManService) {
		this.linkManService = linkManService;
	}
	@Override
	public LinkMan getModel() {
		if (linkMan == null) {
			linkMan = new LinkMan();
		}
		return linkMan;
	}
	
	
	/**
	 * 添加联系人的方法
	 * @return
	 */
	public String save() {
		linkManService.save(linkMan);
		return Constant.SAVE_SUCCESS;
	}
	
	
	/**
	 * 分页查询联系人
	 * @return
	 */
	public String findByPage() {
		DetachedCriteria criteria = DetachedCriteria.forClass(LinkMan.class);
		
		//校验页面的提交  非空校验
		//联系人名称
		if (linkMan.getLkm_name() != null && !StringUtils.isEmpty(linkMan.getLkm_name().trim())) {
			criteria.add(Restrictions.like("lkm_name", "%"+linkMan.getLkm_name()+"%"));
		}
		//联系人电话
		if (linkMan.getLkm_phone() != null && !StringUtils.isEmpty(linkMan.getLkm_phone().trim())) {
			criteria.add(Restrictions.like("lkm_phone", "%"+linkMan.getLkm_phone()+"%"));
		}
		//所属客户
		if (linkMan.getCustomer() != null && !StringUtils.isEmpty(linkMan.getCustomer().getCust_id())) {
			criteria.add(Restrictions.eq("customer.cust_id", linkMan.getCustomer().getCust_id()));
		}
		
		//1.分页查询获取到pageBean对象
		PageBean<LinkMan> pageBean = linkManService.findByPage(criteria,currentPage,pageSize);
		//2.将pageBean对象存储到值栈  由于前面我们做的客户的分页是采用的push,
		//为了方便后面jsp页面的分页工具条的抽取,所以这里统一采用push方式存储
		ActionContext.getContext().getValueStack().push(pageBean);
		return Constant.PAGE_SUCCESS;
	}
	
	
	/**
	 * 删除联系人
	 * @return
	 */
	public String delete() {
		linkManService.delete(linkMan);
		return Constant.DELETE_SUCCESS;
	}
	
	
	/**
	 * 修改联系人的第一步
	 * @return
	 */
	public String edit() {
		editLinkMan = linkManService.findById(linkMan.getLkm_id());
		return Constant.EDIT_SUCCESS;
	}
	
	
	/**
	 * 修改联系人的第二步
	 * @return
	 */
	public String update() {
		linkManService.update(linkMan);
		return Constant.UPDATE_SUCCESS;
	}
	
	public String findByCid() {
		list = linkManService.findByCid(cid);
		return Constant.JSON_SUCCESS;
	}
	
}
