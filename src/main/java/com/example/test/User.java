package com.example.test;

public class User {
    private int id;
    private String name;
    private UserInfo userInfo;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public UserInfo getUserInfo() {
        return userInfo;
    }
    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
    @Override
    public String toString() {
	return "User id="+id+",name="+name+", userinfo="+userInfo.getInfo();
    }
}
