package com.example.beans;

import java.util.HashMap;
import java.util.Map;

import com.example.beans.io.ResourceLoader;

public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {
    
    //用map保存从资源文件中读取的bean定义
    private Map<String, BeanDefinition> registry;
    
    //资源加载器
    private ResourceLoader resourceLoader;
    
    
    protected AbstractBeanDefinitionReader(ResourceLoader resourceLoader) {
	this.resourceLoader=resourceLoader;
	registry=new HashMap<String, BeanDefinition>();
    }



    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }


    public Map<String, BeanDefinition> getRegistry(){
	return registry;
    }

}
