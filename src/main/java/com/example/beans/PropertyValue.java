package com.example.beans;
/*
 * 
 * 封装XML中一个bean的property即成员变量的String和Object的键值配对
 * 
 */
public class PropertyValue {
    private final String name;
    private final Object value;

    public PropertyValue(String name,Object value) {
	this.name=name;
	this.value=value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }
    
}
