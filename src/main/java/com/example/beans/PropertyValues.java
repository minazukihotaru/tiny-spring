package com.example.beans;

import java.util.ArrayList;
import java.util.List;



/*
 * 封装一个对象所有的PropertyValue，相对于对象的成员列表
 * 
 */
public class PropertyValues {
    private final List<PropertyValue> propertyValueList=new ArrayList<PropertyValue>();
    
    public PropertyValues() {}
    
    public void addPropertyValue(PropertyValue pv) {
	propertyValueList.add(pv);
    }
    
    public List<PropertyValue> getPropertyValueList(){
	return propertyValueList;
    }
}
