package com.example.beans.factory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.example.beans.BeanDefinition;
import com.example.beans.BeanReference;
import com.example.beans.PropertyValue;

/*
 * 继承bean工厂抽象类
 * 需要实现属性注入的方法applyPropertyValues
 * 
 */
public class AutowireCapableBeanFactory extends AbstractBeanFactory {

    /*
     * 
     * 通过反射自动装配bean的所有属性
     * 
     */
    @Override
    protected void applyPropertyValues(Object bean, BeanDefinition beanDefinition) throws Exception {
	/*
	 * TODO if (bean instanceof BeanFactoryAware) { ((BeanFactoryAware)
	 * bean).setBeanFactory(this); }
	 */

	for (PropertyValue propertyValue : beanDefinition.getPropertyValues().getPropertyValueList()) {
	    Object value = propertyValue.getValue();
	    if (value instanceof BeanReference) {
		// 如果value是ref，那就把ref中的bean装到value里
		BeanReference beanReference = (BeanReference) value;
		value = getBean(beanReference.getName());
	    }
	    // 接下来把value放入bean对象的对应成员变量中,用bean对象的set方法
	    try {
		// 拼接setter方法名，如属性名为id，则方法名为setId
		String setterMethodName = "set" + propertyValue.getName().substring(0, 1).toUpperCase()
			+ propertyValue.getName().substring(1);

		Method setterMethod = bean.getClass().getDeclaredMethod(setterMethodName, value.getClass());
		setterMethod.setAccessible(true);// 可访问性
		setterMethod.invoke(bean, value);// 让bean对象执行setterMethod方法，参数为value
	    } catch (NoSuchMethodException e) {
		//如果没有对应的setter方法，那就直接把value塞给bean的成员
		
		Field field = bean.getClass().getDeclaredField(propertyValue.getName());
		field.setAccessible(true);
		field.set(bean,value);//把bean对象的field成员赋值为value
	    }

	}

    }
}
