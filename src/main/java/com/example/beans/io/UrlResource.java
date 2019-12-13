package com.example.beans.io;


import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class UrlResource implements Resource {
    private final URL url;
    //通过url获取xml文件
    public UrlResource(URL url) {
	this.url=url;
    }
    
    @Override
    public InputStream getInputStream() throws Exception {
	URLConnection urlConnection = url.openConnection();
	urlConnection.connect();
	return urlConnection.getInputStream();
    }
}
