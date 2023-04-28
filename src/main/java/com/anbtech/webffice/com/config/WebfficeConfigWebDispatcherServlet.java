package com.anbtech.webffice.com.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.anbtech.webffice.com.interceptor.AuthenticInterceptor;
import com.anbtech.webffice.com.interceptor.CustomAuthenticInterceptor;

/**
 * @ClassName : WebfficeConfigWebDispatcherServlet.java
 * @Description : DispatcherServlet 설정
 *
 * @since  : 2023. 04. 20
 * @version : 1.0
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일              수정자               수정내용
 *  -------------  ------------   ---------------------
 * </pre>
 *
 */
@Configuration
@ComponentScan(basePackages = "com.anbtech.webffice", excludeFilters = {
	@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Service.class),
	@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Repository.class),
	@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Configuration.class)
})
public class WebfficeConfigWebDispatcherServlet implements WebMvcConfigurer {

	// =====================================================================
	// RequestMappingHandlerMapping 설정
	// =====================================================================
	// -------------------------------------------------------------
	// RequestMappingHandlerMapping 설정 - Interceptor 추가
	// -------------------------------------------------------------
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new AuthenticInterceptor())
			.addPathPatterns(
//				"/cop/com/*.do",
				"/uat/uia/*.do")
			.excludePathPatterns(
				"/uat/uia/actionLogin.do",
				"/uat/uia/actionLoginAPI.do",
				"/uat/uia/actionLoginJWT.do",
				"/uat/uia/egovLoginUsr.do",
				"/uat/uia/egovLoginUsrAPI.do",
				"/uat/uia/actionLogoutAPI.do"
				);
		registry.addInterceptor(new CustomAuthenticInterceptor())
			.addPathPatterns(
				"/**/*.do")
			.excludePathPatterns(
				"/uat/uia/**");
	}

}
