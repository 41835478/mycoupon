package com.cloudjet.coupon.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	static String formatDate = "yyyy-MM-dd";
	static String formatDate2 = "yyyy-MM-dd hh:mm:ss";
	static String formatDate3 = "yyyy年MM月dd日";
	
	// 获得当天0点时间
	public Date getTimesmorning() {
	Calendar cal = Calendar.getInstance();
	cal.set(Calendar.HOUR_OF_DAY, 0);
	cal.set(Calendar.SECOND, 0);
	cal.set(Calendar.MINUTE, 0);
	cal.set(Calendar.MILLISECOND, 0);
	return cal.getTime();
	}

	// 获得当天24点时间
	public Date getTimesnight() {
	Calendar cal = Calendar.getInstance();
	cal.set(Calendar.HOUR_OF_DAY, 24);
	cal.set(Calendar.SECOND, 0);
	cal.set(Calendar.MINUTE, 0);
	cal.set(Calendar.MILLISECOND, 0);
	return cal.getTime();
	}

	// 获得本周一0点时间
	public Date getTimesWeekmorning() {
	Calendar cal = Calendar.getInstance();
	cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
	cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
	return cal.getTime();
	}

	// 获得本周日24点时间
	public Date getTimesWeeknight() {
	Calendar cal = Calendar.getInstance();
	cal.setTime(getTimesWeekmorning());
	cal.add(Calendar.DAY_OF_WEEK, 7);
	return cal.getTime();
	}
	
	// 获得本周日23点59:59
	public Date getTimesWeeknight23() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getTimesWeekmorning());
		cal.add(Calendar.DAY_OF_WEEK, 6);
		
		SimpleDateFormat sf = new SimpleDateFormat(formatDate);
		String resultDateStr = sf.format(cal.getTime())+" 23:59:59";
		Date resultDate = null;
		try {
			SimpleDateFormat sf2 = new SimpleDateFormat(formatDate2);
			resultDate = sf2.parse(resultDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return resultDate;
		}

	// 获得本月第一天0点时间
	public Date getTimesMonthmorning() {
	Calendar cal = Calendar.getInstance();
	cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
	cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
	return cal.getTime();
	}

	// 获得本月最后一天24点时间
	public Date getTimesMonthnight() {
	Calendar cal = Calendar.getInstance();
	cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
	cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
	cal.set(Calendar.HOUR_OF_DAY, 24);
	return cal.getTime();
	}
	
	//获取一年后的时间
	public Date getTimesYearnight(){
		Date todayDate=new Date();    
		long afterTime=(todayDate.getTime()/1000)+60*60*24*365;      
		Date afterDate = new Date(afterTime*1000);
		return afterDate;
	}

	//固定格式的时间，用于测试
	public Date getTime(String time){
		SimpleDateFormat sdf = new SimpleDateFormat(formatDate2);
		Date date=null;
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	//获取一年后的时间
	public static String getTimeDay(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat(formatDate3);
		sdf.format(date);
		return sdf.format(date);
	}
	
	public static void main(String[] args){
		System.out.println(DateUtil.getTimeDay(new Date()));
	}
}
