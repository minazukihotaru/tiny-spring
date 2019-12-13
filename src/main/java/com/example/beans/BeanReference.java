package com.example.beans;

/*
 * 
 * bean的属性中的ref
 * 
 */
public class BeanReference {
    private String ref;
    private Object bean;
    
    public BeanReference(String ref) {
	this.ref=ref;
    }
    

    public String getRef() {
        return ref;
    }


    public void setRef(String ref) {
        this.ref = ref;
    }


    public Object getBean() {
        return bean;
    }
    public void setBean(Object bean) {
        this.bean = bean;
    }
    
}
