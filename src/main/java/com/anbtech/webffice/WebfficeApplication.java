package com.anbtech.webffice;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Import;

import com.anbtech.webffice.com.config.WebfficeWebApplicationInitializer;

@ServletComponentScan
@SpringBootApplication
@Import({WebfficeWebApplicationInitializer.class})
public class WebfficeApplication {
	public static void main(String[] args) {
		System.out.println("##### WebfficeApplication Start #####");

		SpringApplication springApplication = new SpringApplication(WebfficeApplication.class);
		springApplication.setBannerMode(Banner.Mode.OFF);
		springApplication.run(args);

		System.out.println("##### WebfficeApplication End #####");
	}

}
