package com.zlx.im.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyTime {

	// 06-12 08:32:33-->date-->long

	/**
	 * current
	 * 
	 * @return
	 */
	public static String geTime() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat formate = new SimpleDateFormat("MM-dd HH:mm:ss");
		return formate.format(date);
	}

	/**
	 * long-->String
	 * 
	 * @param time
	 * @return
	 */
	public static String geTime(Long time) {
		Date date = new Date(time);
		SimpleDateFormat formate = new SimpleDateFormat("MM-dd HH:mm:ss");
		return formate.format(date);
	}

	/**
	 * String-->long
	 * 
	 * @param dateString
	 * @return
	 * @throws ParseException
	 */
	public static Long geTime(String dateString) throws ParseException {
		SimpleDateFormat formate = new SimpleDateFormat("MM-dd HH:mm:ss");
		return formate.parse(dateString).getTime();
	}
}
