package com.jian.ssm.annotation;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.jian.ssm.dao.SysLogDao;
import com.jian.ssm.entity.SysAdmin;


/**
 * 
 * @ClassName:  SysLogAscept   
 * @Description:TODO   
 * @author: JianLinWei
 * @date:   2018年8月22日 上午11:59:21   
 *
 */
@Aspect
@Component
public class SysLogAscept {
	@Resource
	SysLogDao   sld ;
	public static final Log  log  =   LogFactory.getLog(SysLogAscept.class);
	
	@Pointcut("@annotation(com.jian.ssm.annotation.SysLog)")
	public  void controllerAscept(){
		
	}
	
	/**
	 * 
	 * @Title: before   
	 * @Description: 前置通知 连接点  拦截Controller用户操作 
	 * @param: @param joinPoint 
	 * @author: JianLinWei     
	 * @return: void      
	 * @throws
	 */
	@Before("controllerAscept()")
	public void  before(JoinPoint  joinPoint){
		SimpleDateFormat  format  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();    
		
		 String ip  = getRemoteAddr(request);
		 String params = ""; 
	        if (joinPoint.getArgs() !=  null && joinPoint.getArgs().length > 0) {    
	            for ( int i = 0; i < joinPoint.getArgs().length; i++) {    
	           	 params+=joinPoint.getArgs()[i]+";";   
	           }    
	       }  
		 SysAdmin  admin  =  (SysAdmin) SecurityUtils.getSubject().getPrincipal();
		try{
			String  operation  = getControllerMethodDescription(joinPoint);
			String username = admin.getUsername();
			com.jian.ssm.entity.SysLog  syslog  = new com.jian.ssm.entity.SysLog();
			syslog.setIp(ip);
			syslog.setCreatetime(format.format(new Date()));
			syslog.setOperation(operation);
			syslog.setUsername(username);
			syslog.setParam(params);
			syslog.setBelongid(admin.getBelongid());
			sld.insert(syslog);
		}catch(Exception e){
			/*e.printStackTrace();*/
			log.info(e.getMessage());
		}
		
		
	}

	
	/**
	 * 
	 * @Title: getControllerMethodDescription   
	 * @Description: 获取方法描述  
	 * @param: @param joinPoint
	 * @param: @return
	 * @param: @throws Exception 
	 * @author: JianLinWei     
	 * @return: String      
	 * @throws
	 */
	 @SuppressWarnings("rawtypes")
	  public  static String getControllerMethodDescription(JoinPoint joinPoint)  throws Exception {    
	        String targetName = joinPoint.getTarget().getClass().getName();    
	        String methodName = joinPoint.getSignature().getName();    
	        Object[] arguments = joinPoint.getArgs();    
	        Class targetClass = Class.forName(targetName);    
	        Method[] methods = targetClass.getMethods();    
	        String description = "";    
	         for (Method method : methods) {    
	             if (method.getName().equals(methodName)) {    
	               
					Class[] clazzs = method.getParameterTypes();    
	                 if (clazzs.length == arguments.length) {    
	                    description = method.getAnnotation(SysLog. class).value();    
	                     break;    
	                }    
	            }    
	        }    
	         return description;    
	    }    
	     
	 /**
	  * 
	  * @Title: getRemoteAddr   
	  * @Description: TODO   
	  * @param: @param request
	  * @param: @return 
	  * @author: JianLinWei     
	  * @return: String      
	  * @throws
	  */
	 public static String getRemoteAddr(HttpServletRequest request) {
	 		String ip = request.getHeader("X-Forwarded-For");
	 		if (StringUtils.isBlank(ip) || StringUtils.equalsIgnoreCase(ip, "unknown")) {
	 			ip = request.getHeader("Proxy-Client-IP");
	 		}
	 		if (StringUtils.isBlank(ip) || StringUtils.equalsIgnoreCase(ip, "unknown")) {
	 			ip = request.getHeader("WL-Proxy-Client-IP");
	 		}
	 		if (StringUtils.isBlank(ip) || StringUtils.equalsIgnoreCase(ip, "unknown")) {
	 			ip = request.getHeader("HTTP_CLIENT_IP");
	 		}
	 
	 		// 民安赖波经理提供的从head的什么值获取IP地址
	 		if (StringUtils.isBlank(ip) || StringUtils.equalsIgnoreCase(ip, "unknown")) {
	 			ip = request.getHeader("X-Real-IP");
	 		}
	 
	 		if (StringUtils.isBlank(ip) || StringUtils.equalsIgnoreCase(ip, "unknown")) {
	 			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
	 		}
	 		if (StringUtils.isBlank(ip) || StringUtils.equalsIgnoreCase(ip, "unknown")) {
	 			ip = request.getRemoteAddr();
	 		}
	 		if (StringUtils.isNotBlank(ip) && StringUtils.indexOf(ip, ",") > 0) {
	 			String[] ipArray = StringUtils.split(ip, ",");
	 			ip = ipArray[0];
	 		}
	 		return ip;
	 	}
	 
}
