package com.jian.ssm.util;


public class PageUtil {
	public  int befor ;
	public  int after ;
	
	/**
	 * 
	 * @Title: getRowBounds   
	 * @Description: 获取分页RowBounds   
	 * @param: @param page
	 * @param: @param limit
	 * @param: @return 
	 * @author: JianLinWei     
	 * @return: RowBounds      
	 * @throws
	 */
      public  static PageUtil  getRowBounds(String page ,String limit){
    	  int befor = Integer.parseInt(limit) * (Integer.parseInt(page) - 1) + 1;
  		int after = Integer.parseInt(page) * Integer.parseInt(limit);
  		
  		PageUtil  pu = new PageUtil();
  		pu.after = after ;
  		pu.befor = befor ;
		return pu;
  		
      }
}
