package com.example.beans;

/*
 * 
 * bean的属性中的ref
 * 
 */
public class BeanReference {
    private String name;
    private Object bean;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Object getBean() {
        return bean;
    }
    public void setBean(Object bean) {
        this.bean = bean;
    }
    
}
