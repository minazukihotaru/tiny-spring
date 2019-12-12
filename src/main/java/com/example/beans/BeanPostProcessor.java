package com.example.beans;

/*
 * 
 * 接口定义bean初始化的操作
 * 
 */
public interface BeanPostProcessor {
    
    // before initialization
    Object postProcessBeforeInitialization(Object bean, String beanName)throws Exception;
    
    // after initialization
    Object postProcessAfterInitialization(Object bean, String beanName)throws Exception;
    
}
