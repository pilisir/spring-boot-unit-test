
package com.mywebsb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

//@Configuration
//@EnableAutoConfiguration
//@ComponentScan
@SpringBootApplication
public class MyWebSBApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MyWebSBApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(MyWebSBApplication.class, args);
	}

}
