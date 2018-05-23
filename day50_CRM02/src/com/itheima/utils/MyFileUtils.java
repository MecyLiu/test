package com.itheima.utils;

import java.util.UUID;

public class MyFileUtils {
	
	/**
	 * 生成UUID的文件名称
	 * @param fileName
	 * @return
	 */
	public static String getFileName(String fileName) {
		String prefix = UUID.randomUUID().toString().replaceAll("_", "");
		String suffix = fileName.substring(fileName.lastIndexOf("."));
		return prefix + suffix;
	}
}
