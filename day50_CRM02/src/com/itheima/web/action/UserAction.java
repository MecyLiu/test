package com.itheima.web.action;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.itheima.bean.User;
import com.itheima.constant.Constant;
import com.itheima.service.UserService;
import com.itheima.utils.MD5Utils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.dsna.util.images.ValidateCode;

public class UserAction extends ActionSupport implements ModelDriven<User>{
	
	private String txtCode;
	public void setTxtCode(String txtCode) {
		this.txtCode = txtCode;
	}
	private User user;
	@Override
	public User getModel() {
		if (user == null) {
			user = new User();
		}
		return user;
	}
	
	@Autowired
	private UserService userService;
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	/**
	 * 生成验证码
	 * @return
	 * @throws IOException 
	 */
	public String createCode() throws IOException {
		ValidateCode validateCode = new ValidateCode(130, 50, 4, 8);
		//得到4个文字
		String code = validateCode.getCode();
		//使用session存储验证码
		ServletActionContext.getRequest().getSession().setAttribute("code", code);
		//把图片写给页面
		validateCode.write(ServletActionContext.getResponse().getOutputStream());
		return NONE;
	}
	
	
	/**
	 * 用户注册的方法
	 */
	public String regist() {
		//设置用户的状态为1
		user.setUser_state('1');
		//对用户密码进行加密
		String encodPwd = MD5Utils.encodPwd(user.getUser_password());
		//将加密后的密码设置到user对象中
		user.setUser_password(encodPwd);
		userService.regist(user);
		return NONE;
	}
	
	public String login() {
		//校验验证码
		HttpSession session = ServletActionContext.getRequest().getSession();
		String code = (String) session.getAttribute("code");
		System.out.println(code);
		System.out.println(txtCode);
		//将验证码从session中移除掉
		session.removeAttribute("code");
		if (!code.equalsIgnoreCase(txtCode)) {
			addActionError("验证码错误!");//存数据到值栈
			return Constant.LOGIN_ERROR;
		}
		
		//加密密码
		String encodPwd = MD5Utils.encodPwd(user.getUser_password());
		user.setUser_password(encodPwd);
		User loginUser = userService.login(user);
		if (loginUser != null) {
			//登录成功,存储对象到session
			ServletActionContext.getRequest().getSession().setAttribute("user", loginUser);
			//重定向到首页,头部显示欢迎XXX登录
			return Constant.LOGIN_SUCCESS;
		}
		//登录失败,提示 用户名或密码错误,跳转到登录页面
		addActionError("用户名或密码错误!");//数据存到值栈
		return Constant.LOGIN_ERROR;

	}
}
