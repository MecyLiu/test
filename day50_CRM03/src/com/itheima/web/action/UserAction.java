package com.itheima.web.action;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bean.User;
import com.itheima.constant.Constant;
import com.itheima.service.UserService;
import com.itheima.utils.MD5Utils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
public class UserAction extends ActionSupport implements ModelDriven<User>{
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
	
	/**
	 * 用户注册的方法
	 */
	@Action(value="user_regist")
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
	
	@Action(value="user_login" , results={@Result(name="login_success",location="/index.jsp"),@Result(name="login_failed",location="/login.jsp")})
	public String login() {
			//加密密码
			String encodPwd = MD5Utils.encodPwd(user.getUser_password());
			user.setUser_password(encodPwd);
			User loginUser = userService.login(user);
			if (loginUser != null) {
				//登录成功,存储对象到session
				ServletActionContext.getRequest().getSession().setAttribute("user", loginUser);
				//重定向到首页,头部显示欢迎XXX登录
				return Constant.LOGIN_SUCCESS;
			}else {
				//登录失败,提示 用户名或密码错误,跳转到登录页面
				addActionError("用户名或密码错误!");//数据存到值栈
				return Constant.LOGIN_ERROR;
			}
	}
}
