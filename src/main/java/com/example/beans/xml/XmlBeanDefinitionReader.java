package com.example.beans.xml;


import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.example.beans.AbstractBeanDefinitionReader;
import com.example.beans.BeanDefinition;
import com.example.beans.BeanReference;
import com.example.beans.PropertyValue;
import com.example.beans.io.ResourceLoader;


public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    public XmlBeanDefinitionReader(ResourceLoader resourceLoader) {
	super(resourceLoader);
	// TODO 自动生成的构造函数存根
    }

    @Override
    public void loadBeanDefinitions(String location) throws Exception {
	InputStream inputStream = this.getResourceLoader().getResource(location).getInputStream();
	
	doLoadBeanDefintions(inputStream);
    }
    
    
    

    /*
     *   loadBeanDefinitions(String) → 载入bean定义，将InputStream转换成Document
     */
    protected void doLoadBeanDefintions(InputStream inputStream) throws Exception {
	//根据inputStream获取Document
	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	DocumentBuilder builder = factory.newDocumentBuilder();
	Document doc = builder.parse(inputStream);
	readDocument(doc);
	inputStream.close();
    }
    
    
    
    

    /*
     * 
     * loadBeanDefinitions(String) → 载入bean定义，将InputStream转换成Document → 读取Document,取出beans标签
     * 
     */
    protected void readDocument(Document doc) throws Exception {
	
	NodeList beansList = doc.getElementsByTagName("bean");
	
	for(int i=0;i<beansList.getLength();i++) {
	    Node node = beansList.item(i);
	    if(node instanceof Element) {
		Element bean = (Element)node;
		readBeanTag(bean);
	    }
	}
	
	
	
	/*
	NodeList beansTag = doc.getElementsByTagName("beans");
	for(int i=0;i<beansTag.getLength();i++) {
	    NodeList beans = beansTag.item(i).getChildNodes();
	    for(int j=0;j<beans.getLength();j++) {
		Node nodeBean = beans.item(j);
		if(nodeBean instanceof Element) {
		    readBeanTag((Element)nodeBean);
		}
	    }
	}
	*/
    }
    
    
    
    
    
    
    
    
    /*
     * 
     * loadBeanDefinitions(String) → 载入bean定义，将InputStream转换成Document → 读取Document,取出beans标签
     *  → 读取beans标签每一个子节点bean标签
     * 
     */
    protected void readBeanTag(Element ele) throws Exception {
	String beanName = ele.getAttribute("id");
	String beanClassName = ele.getAttribute("class");
	
	BeanDefinition beanDefinition = new BeanDefinition();
	beanDefinition.setBeanClass(Class.forName(beanClassName));
	beanDefinition.setBeanClassName(beanClassName);

	
	//读取bean标签的子节点property,并存入bean定义中
	readBeanPropertys(ele,beanDefinition);
	
	//将得到的bean定义放入registry中
	this.getRegistry().put(beanName, beanDefinition);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    /*
     * 
     * loadBeanDefinitions(String) → 载入bean定义，将InputStream转换成Document → 读取Document,取出beans标签
     *  → 读取beans标签每一个子节点bean标签 → 读取bean标签的每一个子节点property标签
     * 
     */
    protected void readBeanPropertys(Element ele, BeanDefinition beanDefinition) throws Exception {
	
	NodeList propertyNodes = ele.getElementsByTagName("property");
	
	for(int i=0;i<propertyNodes.getLength();i++) {
	    
	    Node propertyNode = propertyNodes.item(i);
	    
	    if(propertyNode instanceof Element) {
		
		Element propertyEle = (Element)propertyNode;
		String propertyName = propertyEle.getAttribute("name");
		String propertyValue = propertyEle.getAttribute("value");
		
		if(propertyValue != null && propertyValue.length()>0) {
		    //普通属性，不是ref
		    beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(propertyName, propertyValue));
		}
		else {
		    //是ref
		    String ref = propertyEle.getAttribute("ref");
		    if(ref == null || ref.length()==0) {
			throw new IllegalArgumentException("<property>标签"+propertyName+"中没有value或者ref");
		    }
		    
		    BeanReference beanReference = new BeanReference(ref);
		    beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(propertyName, beanReference));
		}
	    }
	}
    }
}
