package com.example.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.example.beans.BeanDefinition;
import com.example.beans.BeanReference;
import com.example.beans.PropertyValue;
import com.example.beans.PropertyValues;
import com.example.beans.factory.AutowireCapableBeanFactory;
import com.example.beans.io.ResourceLoader;
import com.example.beans.io.UrlResourceLoader;
import com.example.beans.xml.XmlBeanDefinitionReader;




public class TestApplication {
    /*
     * 
     * @ IOC容器实现
     * @ 2019-12-12
     */
    @Test
    public void testIOC() throws Exception {

	// 初始化工厂
	AutowireCapableBeanFactory beanFactory = new AutowireCapableBeanFactory();

	// 定义UserInfo类的bean
	BeanDefinition beanDefinition_UserInfo = new BeanDefinition();
	beanDefinition_UserInfo.setBeanClassName("com.example.test.UserInfo");
	beanDefinition_UserInfo.setBeanClass(Class.forName("com.example.test.UserInfo"));

	// 将UserInfo类的属性列表放入UserInfo的bean定义中
	PropertyValue pv_UserInfo_info = new PropertyValue("info", "用户信息");
	PropertyValues pvs_UserInfo = new PropertyValues();
	pvs_UserInfo.addPropertyValue(pv_UserInfo_info);
	beanDefinition_UserInfo.setPropertyValues(pvs_UserInfo);

	// 在工厂注册UserInfo的bean定义,可通过getBean("getuserinfo")获取一个UserInfo类型的bean
	beanFactory.registerBeanDefinition("getuserinfo", beanDefinition_UserInfo);

	// 定义User类的bean
	BeanDefinition beanDefinition_User = new BeanDefinition();
	beanDefinition_User.setBeanClassName("com.example.test.User");
	beanDefinition_User.setBeanClass(Class.forName("com.example.test.User"));

	// User类的属性列表中有ref
	BeanReference ref = new BeanReference("getuserinfo");

	// 将User类的属性列表放入User的bean定义中
	PropertyValue pv_User_id = new PropertyValue("id", "4396");
	PropertyValue pv_User_name = new PropertyValue("name", "小明");
	PropertyValue pv_User_userInfo = new PropertyValue("userInfo", ref);
	PropertyValues pvs_User = new PropertyValues();
	pvs_User.addPropertyValue(pv_User_id);
	pvs_User.addPropertyValue(pv_User_name);
	pvs_User.addPropertyValue(pv_User_userInfo);
	beanDefinition_User.setPropertyValues(pvs_User);

	// 在工厂注册User的bean定义,可通过getBean("getuser")获取一个User类型的bean
	beanFactory.registerBeanDefinition("getuser", beanDefinition_User);

	User user1 = (User) beanFactory.getBean("getuser");
	System.out.println(user1);

    }
    
    
    
    
    

    /*
     * @测试从Xml文件中获取bean定义
     * @ 2019-12-13
     */
    @Test
    public void testXmlIOC() throws Exception {
	XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(new UrlResourceLoader());
	xmlBeanDefinitionReader.loadBeanDefinitions("testXmlIoc.xml");
	Map<String, BeanDefinition> xmlRegistry = xmlBeanDefinitionReader.getRegistry();
	
	AutowireCapableBeanFactory factory = new AutowireCapableBeanFactory();
	for(String beanName : xmlRegistry.keySet()) {
	    factory.registerBeanDefinition(beanName, xmlRegistry.get(beanName));
	}
	
	User XiaoMing = (User) factory.getBean("userXiaoMing");
	System.out.println(XiaoMing);
	/*
	<bean id="userXiaoMing" class="com.example.test.User">
		<property name="id" value="4396"/>
		<property name="name" value="小明"/>
		<property name="userInfo" ref="infoXiaoMing"/>
	</bean>

	<bean id="infoXiaoMing" class="com.example.test.UserInfo">
		<property name="info" value="小明的用户信息"/>
	</bean>
	*/
    }
    
    
    
    
    
    
    
    
    
    
    
    
    /*
     * 
     * @测试读取xml文件
     * 
     * 
     */
    @Test
    public void testReadXml() throws IOException, ParserConfigurationException, SAXException {
	URL url = getClass().getClassLoader().getResource("testXmlIoc.xml");
	URLConnection connection = url.openConnection();
	connection.connect();
	InputStream inputStream = connection.getInputStream();
	
	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	DocumentBuilder builder = factory.newDocumentBuilder();
	Document doc = builder.parse(inputStream);
	

	
	NodeList beanList = doc.getElementsByTagName("bean");
	System.out.println("beanListsize="+beanList.getLength());
	
	Node bean_1 = beanList.item(0);
	Element ele_bean_1 = (Element)bean_1;
	System.out.println("bean1       id="+ele_bean_1.getAttribute("id"));
	
	NodeList propertyList = ele_bean_1.getElementsByTagName("property");// element.get...方法获取element的子节点
	Node property_1 = propertyList.item(0);
	Element ele_property_1 = (Element) property_1;
	System.out.println("property1       name="+ ele_property_1.getAttribute("name"));

	
    }
}
