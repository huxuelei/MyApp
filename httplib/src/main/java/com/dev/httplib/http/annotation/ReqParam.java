package com.dev.httplib.http.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注意：只能标注在string类型上面
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ReqParam {

	/**
	 * 键值对的键名称，如果为空则取字段名称
	 * 
	 * @return
	 */
	public String value() default "";

	/**
	 * 参数是否是必须的，如果不是必须的，切赋值为空，那么则不传输，如果是必须的，且为空，则报错
	 * 
	 * @return
	 */
	public boolean required() default false;

	/**
	 * 是否作为参数传递，默认是传递的
	 * 
	 * @return
	 */
	public boolean asParam() default true;

	/**
	 * 加密类型
	 * 
	 * @return
	 */
	public SecurityType security() default SecurityType.None;
}