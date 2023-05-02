package com.anbtech.webffice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Import;

import com.anbtech.webffice.com.config.WebfficeWebApplicationInitializer;

@ServletComponentScan
@Import({WebfficeWebApplicationInitializer.class})
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@MapperScan(basePackages = "com.anbtech.webffice")
public class WebfficeApplication {
	public static void main(String[] args) {
		System.out.println("##### WebfficeApplication Start #####");

		SpringApplication springApplication = new SpringApplication(WebfficeApplication.class);
		springApplication.setBannerMode(Banner.Mode.OFF);
		springApplication.run(args);

		System.out.println("##### WebfficeApplication End #####");
	}

}
