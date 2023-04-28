package com.anbtech.webffice.com.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@Import({
	WebfficeConfigAppDatasource.class,
	WebfficeConfigAppMapper.class,
})
@PropertySources({
	@PropertySource("classpath:/application.properties")
}) //CAUTION: min JDK 8
public class WebfficeConfigApp {

}
