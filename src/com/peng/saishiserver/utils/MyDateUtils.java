package com.peng.saishiserver.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * 时间的工具类
 * 
 * @author peng 1.获得当前的时间
 */
public class MyDateUtils {

	// 获得当前的时间
	public static String getNowTime() {
		Date currDate = Calendar.getInstance().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		return  sdf.format(currDate);
	}
	
	// 获得当前的时间
	public static String getNowTime2() {
		Date currDate = Calendar.getInstance().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 HH:mm");
		return  sdf.format(currDate);
	}

}
