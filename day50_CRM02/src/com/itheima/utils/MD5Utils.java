package com.itheima.utils;

import org.springframework.util.DigestUtils;

public class MD5Utils {
	
	/**
	 * 使用MD5加密用户密码的方法
	 * @param password
	 * @return
	 */
	public static String encodPwd(String password) {
		for(int i=0; i<10; i++){
			password = DigestUtils.md5DigestAsHex(password.getBytes());
		}
		return password;
	}
}
