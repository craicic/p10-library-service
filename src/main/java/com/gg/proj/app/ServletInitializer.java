package com.gg.proj.app;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * This class is useful when you need to run the war package on the tomcat server : it define the entry point. See spring
 * words on it :
 * SpringBootServletInitializer is an extension of WebApplicationInitializer. It is needed to run a SpringApplication
 * from a WAR deployment. The implementing classes are supposed to provided the source configuration class by extending
 * configure() method.
 */
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(LibraryServiceApplication.class);
    }

}

