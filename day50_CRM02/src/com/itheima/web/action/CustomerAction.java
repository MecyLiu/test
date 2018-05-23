package com.itheima.web.action;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.itheima.bean.Customer;
import com.itheima.bean.PageBean;
import com.itheima.bean.User;
import com.itheima.constant.Constant;
import com.itheima.service.CustomerService;
import com.itheima.utils.MyFileUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class CustomerAction extends ActionSupport implements ModelDriven<Customer>{
	
	private List<Customer> list;
	public List<Customer> getList() {
		return list;
	}
	
	private int currentPage = 1;//默认当前页是第一页
	private int pageSize = 5;//默认每页显示数据条数为5条
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	private File upload;//文件对象 = 页面写的name的属性值
	private String uploadContentType;//文件类型 = name值+ContextType
	private String uploadFileName;//文件名称 = name值+FileName
	public void setUpload(File upload) {
		this.upload = upload;
	}
	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	
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
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	public String findAll() {
		list = customerService.findAll();
		return Constant.JSON_SUCCESS;
	}
	
	
	/**
	 * 添加客户的方法
	 * @return
	 * @throws IOException 
	 */
	public String add() throws IOException {
		//struts框架收到文件后，会把它存储成一个临时文件，我们需要再把这个文件转存成我们想要的文件，当然地址我们就可以自己给了。
		String fileName = MyFileUtils.getFileName(uploadFileName);
		File file = new File("D:/itheima30/img",fileName);
		FileUtils.copyFile(upload, file);
		
		//在走下一层的逻辑前，先完成数据的校验。
		//只要这些数据为空，就直接return 
		//1. 校验客户名称
		if (StringUtils.isEmpty(customer.getCust_name().trim())) {
			addActionError("客户名称不能为空!");
			return Constant.INPUT_ERROR;
		}
		//2.校验所属行业
		if (StringUtils.isEmpty(customer.getCust_industry().getDict_id())) {
			addActionError("所属行业不能为空!");
			return Constant.INPUT_ERROR;
		}
		//3.校验信息来源
		if (StringUtils.isEmpty(customer.getCust_source().getDict_id())) {
			addActionError("信息来源不能为空!");
			return Constant.INPUT_ERROR;
		}
		//4.校验客户级别
		if (StringUtils.isEmpty(customer.getCust_level().getDict_id())) {
			addActionError("客户级别不能为空!");
			return Constant.INPUT_ERROR;
		}
		//5.校验客户地址
		if (StringUtils.isEmpty(customer.getCust_address().trim())) {
			addActionError("客户地址不能为空!");
			return Constant.INPUT_ERROR;
		}
		//6.校验联系电话
		if (StringUtils.isEmpty(customer.getCust_phone().trim())) {
			addActionError("联系电话不能为空!");
			return Constant.INPUT_ERROR;
		}
		
		//7.校验客户资质  -->文件
		
		
		//这个customer缺少创建人和负责人
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		customer.setCust_create_id(user);//创建人
		customer.setCust_user_id(user);//负责人
		//添加客户的时候,把客户的图片地址给加进去
		customer.setCust_img("img/"+fileName);
		customerService.add(customer);
		return NONE;
	}
	
	
	/**
	 * 客户列表分页显示的方法
	 * @return
	 */
	public String findByPage() {
		//离线对象: 如果没有给它添加条件，等同于  : selct * from customer ;
		DetachedCriteria criteria = DetachedCriteria.forClass(Customer.class);
		
		//判断筛选条件,条件非空,就添加条件
		//客户名称
		if (customer.getCust_name() != null && !StringUtils.isEmpty(customer.getCust_name().trim())) {
			//预见后面dao的语句  String sql = " select  * from customer  where cust_name like %客户名称% limit ? , ? "
			criteria.add(Restrictions.like("cust_name", "%"+customer.getCust_name()+"%"));
		}
		//所属来源
		if (customer.getCust_source() != null && !StringUtils.isEmpty(customer.getCust_source().getDict_id())) {
			//因为这个id值不是我们客户表里面的字段值，它是属于字典表里面的字段，所以要写的这个属性名应该是 字典对象.dict_id
			criteria.add(Restrictions.eq("cust_source.dict_id", customer.getCust_source().getDict_id()));
		}
		//所属行业
		if (customer.getCust_industry() != null && !StringUtils.isEmpty(customer.getCust_industry().getDict_id())) {
			criteria.add(Restrictions.eq("cust_industry.dict_id", customer.getCust_industry().getDict_id()));
		}
		//客户级别
		if (customer.getCust_level() != null && !StringUtils.isEmpty(customer.getCust_level().getDict_id())) {
			criteria.add(Restrictions.eq("cust_level.dict_id", customer.getCust_level().getDict_id()));
		}
		
		//要想添加条件，用add方法 
		//criteria.add(Restrictions.eq("age", 18));
		PageBean<Customer> pageBean =  customerService.findByPage(criteria,currentPage,pageSize);
		//存储这个pageBean到值栈，以便页面显示 push  | set  |属性方式
		ActionContext.getContext().getValueStack().push(pageBean);;
		return Constant.PAGE_SUCCESS;
	}
	
	/**
	 * 删除客户的方法
	 * @return
	 */
	public String delete() {
		customerService.delete(customer);
		return Constant.DELETE_SUCCESS;
	}
	
	
	/**
	 * 该方法用于查询指定客户的信息,然后跳转到修改页面显示数据
	 * @return
	 */
	public String edit() {
		//1.根据id查询对应的客户端信息
		Customer editCustomer = customerService.findById(customer.getCust_id());
		//2.存储到值栈  push  set  属性
		// set 的背后用的就是push， 但是它是先把数据存储到一个map 里面去，然后push(map)
		ActionContext.getContext().getValueStack().set("editCustomer", editCustomer);
		return Constant.EDIT_SUCCESS;
	}
	
	/**
	 * 该方法是修改的第二步
	 * @return
	 */
	public String update() {
		User user = (User) ActionContext.getContext().getSession().get("user");
		//由于客户端无法获取到创建人和负责人,所以需要手动设置到user对象中,否则在封装user对象的时候数据不完整,就会报500
		customer.setCust_create_id(user);
		customer.setCust_user_id(user);
		customerService.update(customer);
		return Constant.UPDATE_SUCCESS;
	}
}
