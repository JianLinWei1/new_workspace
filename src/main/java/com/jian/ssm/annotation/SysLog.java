package com.jian.ssm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @ClassName:  SysLog   
 * @Description:TODO   
 * @author: JianLinWei
 * @date:   2018年8月22日 上午11:47:33   
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SysLog {

	 String value() default "";
}
