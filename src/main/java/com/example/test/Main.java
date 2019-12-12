package com.example.test;



import com.example.beans.BeanDefinition;
import com.example.beans.BeanReference;
import com.example.beans.PropertyValue;
import com.example.beans.PropertyValues;
import com.example.beans.factory.AutowireCapableBeanFactory;



public class Main {

    public static void main(String[] args) throws Exception {
	
	
	/*
	 * 
	 * 2019-12-12
	 * 
	 */
	
	//初始化工厂
	AutowireCapableBeanFactory beanFactory = new AutowireCapableBeanFactory();
	
	//定义UserInfo类的bean
	BeanDefinition beanDefinition_UserInfo = new BeanDefinition();
	beanDefinition_UserInfo.setBeanClassName("com.example.test.UserInfo");
	beanDefinition_UserInfo.setBeanClass(Class.forName("com.example.test.UserInfo"));
	
	//将UserInfo类的属性列表放入UserInfo的bean定义中
	PropertyValue pv_UserInfo_info = new PropertyValue("info", "用户信息");
	PropertyValues pvs_UserInfo = new PropertyValues();
	pvs_UserInfo.addPropertyValue(pv_UserInfo_info);
	beanDefinition_UserInfo.setPropertyValues(pvs_UserInfo);
	
	//在工厂注册UserInfo的bean定义,可通过getBean("getuserinfo")获取一个UserInfo类型的bean
	beanFactory.registerBeanDefinition("getuserinfo",beanDefinition_UserInfo);
	
	//定义User类的bean
	BeanDefinition beanDefinition_User = new BeanDefinition();
	beanDefinition_User.setBeanClassName("com.example.test.User");
	beanDefinition_User.setBeanClass(Class.forName("com.example.test.User"));
	
	//User类的属性列表中有ref
	BeanReference ref = new BeanReference();
	ref.setName("getuserinfo");
	ref.setBean(beanFactory.getBean("getuserinfo"));
	
	//将User类的属性列表放入User的bean定义中
	PropertyValue pv_User_id = new PropertyValue("id", new Integer(4396));
	PropertyValue pv_User_name = new PropertyValue("name", "小明");
	PropertyValue pv_User_userInfo = new PropertyValue("userInfo", ref);
	PropertyValues pvs_User = new PropertyValues();
	pvs_User.addPropertyValue(pv_User_id);
	pvs_User.addPropertyValue(pv_User_name);
	pvs_User.addPropertyValue(pv_User_userInfo);
	beanDefinition_User.setPropertyValues(pvs_User);
	
	//在工厂注册User的bean定义,可通过getBean("getuser")获取一个User类型的bean
	beanFactory.registerBeanDefinition("getuser",beanDefinition_User);
	
	
	User user1 = (User)beanFactory.getBean("getuser");
	System.out.println(user1);
	
	/*
	 * 
	 * 2019-12-12
	 * 
	 */
	
	
    }

}
