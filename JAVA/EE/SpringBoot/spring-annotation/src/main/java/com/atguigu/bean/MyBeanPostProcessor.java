package com.atguigu.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * 后置处理器： 在bean初始化前后进行 一些处理工作
 * @author lfy
 */
@Component // 将后置处理器加入到容器中
public class MyBeanPostProcessor implements BeanPostProcessor {

	/**
	 *
	 * @param bean 就是要初始化的bean
	 * @param beanName bean的名字
	 * @return 可以返回原始的bean,也可以自己包装,返回wrapper bean
	 * @throws BeansException
	 */
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("postProcessBeforeInitialization..."+beanName+"=>"+bean);
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("postProcessAfterInitialization..."+beanName+"=>"+bean);
		return bean;
	}

}
