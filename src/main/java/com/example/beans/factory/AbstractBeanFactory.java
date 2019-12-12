package com.example.beans.factory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.example.beans.BeanDefinition;
import com.example.beans.BeanPostProcessor;

/*
 * BeanFactory 的一种抽象类实现，规范了 IoC 容器的基本结构，但是把生成 Bean 的具体实现方式留给子类实现。
 * IoC 容器的结构：AbstractBeanFactory 维护一个 beanDefinitionMap 哈希表用于保存类的定义信息（BeanDefinition）。
 * 获取 Bean 时，如果 Bean 已经存在于容器中，则返回之，否则则调用 doCreateBean 方法装配一个 Bean。
 * （所谓存在于容器中，是指容器可以通过 beanDefinitionMap 获取 BeanDefinition 进而通过其 getBean() 方法获取 Bean。）
 */

public class AbstractBeanFactory implements BeanFactory {

    
    //bean定义保存在线程安全的Map里
    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();
    
    //保存bean的名字
    private final List<String> beanDefinitionNames = new ArrayList<String>();
    
    //bean的处理程序
    private List<BeanPostProcessor> beanPostProcessors = new ArrayList<BeanPostProcessor>();

    
    @Override
    public Object getBean(String name) throws Exception {
	BeanDefinition beanDefinition = beanDefinitionMap.get(name);
	//根据name从Map中获取bean，如果没有就抛异常
	if(beanDefinition==null) {
	    throw new IllegalArgumentException("bean name:"+name+" 不存在");
	}
	
	Object bean = beanDefinition.getBean();
	//如果定义存在但还没装配bean
	if(bean == null) {
	    bean = doCreateBean(beanDefinition);//创建bean
	    bean = initializeBean(bean,name);//初始化bean
	    beanDefinition.setBean(bean);
	}
	return bean;
    }
    
    
    /*
     * 
     * 根据每一个bean的名字都执行一次getBean(name)实现装配所有bean
     * 
     */
    public void preInstantiateSingletons() throws Exception{
	Iterator<String> it = this.beanDefinitionNames.iterator();
	while(it.hasNext()) {
	    String name = it.next();
	    getBean(name);
	}
    }
    
    
    /*
     * 
     * 根据类型获取所有bean实例
     * 
     */
    public List getBeanForType(Class Type)throws Exception{
	List beans=new ArrayList<Object>();
	for(String beanDefinitionName : beanDefinitionNames) {
	    if(Type.isAssignableFrom(beanDefinitionMap.get(beanDefinitionName).getBeanClass())) {
		beans.add(getBean(beanDefinitionName));
	    }
	}
	return beans;
    }
    
    

    
    /*
     * 
     * 注册bean定义
     * 
     */
    public void registerBeanDefinition(String name,BeanDefinition beanDefinition)throws Exception{
	beanDefinitionMap.put(name, beanDefinition);
	beanDefinitionNames.add(name);
    }
    
    
    /*
     * 
     * 增加bean处理程序
     * 
     */
    public void addBeanPostProcessors(BeanPostProcessor beanPostProcessor)throws Exception{
	this.beanPostProcessors.add(beanPostProcessor);
    }
    
    
    //----------------------------------------------------------------------------------------------------
    
    /*
     * 
     * getBean(String) → 装配bean
     * 
     */
    protected Object doCreateBean(BeanDefinition beanDefinition) throws Exception {
	Object bean = createBeanInstance(beanDefinition);//根据bean的Class创建一个对象
	beanDefinition.setBean(bean);
	applyPropertyValues(bean, beanDefinition);//注入属性
	return bean;
    }
    
    
    
    
    /*
     * 
     * getBean(String) → 装配bean → 实例化bean
     * 
     */
    protected Object createBeanInstance(BeanDefinition beanDefinition) throws Exception{
	return beanDefinition.getBeanClass().newInstance();
    }
    
    
    
    /*
     * 
     * getBean(String) → 装配bean → 注入属性
     * 此处由继承本类的子类实现
     * 
     */
    protected void applyPropertyValues(Object bean,BeanDefinition beanDefinition)throws Exception{
	
    }
    
  //----------------------------------------------------------------------------------------------------
    
    /*
     * 
     * getBean(String) → 初始化
     * 可在此进行AOP相关操作
     */
    protected Object initializeBean(Object bean,String beanName)throws Exception{
	for(BeanPostProcessor beanPostProcessor : this.beanPostProcessors) {
	    // before initialization
	    bean = beanPostProcessor.postProcessBeforeInitialization(bean, beanName);
	}
	
	
	
	for(BeanPostProcessor beanPostProcessor : this.beanPostProcessors) {
	    // after initialization
	    bean = beanPostProcessor.postProcessAfterInitialization(bean, beanName);
	}
	
	return bean;
    }

}
