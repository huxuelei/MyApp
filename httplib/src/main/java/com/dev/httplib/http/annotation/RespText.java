package com.dev.httplib.http.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RespText {

	/**
	 * 加密类型
	 * 
	 * @return
	 */
	public SecurityType security() default SecurityType.None;
}