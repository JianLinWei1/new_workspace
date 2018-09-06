package com.jian.ssm.util;



public class TimeStampUtil {

	/**
	 * 
	 * @Title: getDistanceTime   
	 * @Description: TODO   
	 * @param: @param time1
	 * @param: @param time2
	 * @param: @return 
	 * @author: jianlinwei     
	 * @return: long      
	 * @throws
	 */
	public long getDistanceTime(long time1, long time2) {
	    long day = 0;
	    long hour = 0;
	    long min = 0;
	    long sec = 0;
	    long diff;

	    if (time1 < time2) {
	            diff = time2 - time1;
	        } else {
	            diff = time1 - time2;
	        }
	        day = diff / (24 * 60 * 60 * 1000);
	        hour = (diff / (60 * 60 * 1000) - day * 24);
	        min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
	        sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
	        if (sec != 0) return sec  ;
	        return 0;
	}
}
