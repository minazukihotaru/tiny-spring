package com.example.beans.io;

import java.net.URL;

public class UrlResourceLoader implements ResourceLoader {

    @Override
    public Resource getResource(String location) throws Exception {
	URL urlResource = getClass().getClassLoader().getResource(location);
	return new UrlResource(urlResource);
    }

}
