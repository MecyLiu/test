package com.itheima.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.util.StrutsTypeConverter;

public class DateConvertor extends StrutsTypeConverter{

	SimpleDateFormat format  = new SimpleDateFormat("yyyy年MM月dd日");
	
	//收数据
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		//values :就是收到的数据
		
		try {
			return format.parse(values[0]);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	//发数据给页面
	@Override
	public String convertToString(Map context, Object o) {
		
		//o就是要发送给页面的数据
		return format.format(o);
	}

}
