package com.anbtech.webffice.com.config;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.multipart.support.MultipartFilter;
import org.springframework.web.servlet.DispatcherServlet;

import com.anbtech.webffice.com.filter.SimpleCORSFilter;

/**
 * @ClassName : WebfficeWebApplicationInitializer.java
 * @Description : 공통 컴포넌트 3.10 WebfficeWebApplicationInitializer 참조 작성
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
public class WebfficeWebApplicationInitializer implements WebApplicationInitializer {

	private final Logger log = LoggerFactory.getLogger(WebfficeWebApplicationInitializer.class);

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {

		log.info("WebfficeWebApplicationInitializer START-============================================");
		log.debug("WebfficeWebApplicationInitializer START-============================================");
		System.out.print("WebfficeWebApplicationInitializer START-============================================");

		// -------------------------------------------------------------
		// Web ServletContextListener 설정 - System property setting
		// -------------------------------------------------------------
		servletContext.addListener(new com.anbtech.webffice.com.config.WebfficeWebServletContextListener());
		
        //-------------------------------------------------------------
        // Spring CharacterEncodingFilter 설정
        //-------------------------------------------------------------
        FilterRegistration.Dynamic characterEncoding = servletContext.addFilter("encodingFilter", new org.springframework.web.filter.CharacterEncodingFilter());
        characterEncoding.setInitParameter("encoding", "UTF-8");
        characterEncoding.setInitParameter("forceEncoding", "true");
        characterEncoding.addMappingForUrlPatterns(null, false, "*.do");
        
        //-------------------------------------------------------------
        // Tomcat의 경우 allowCasualMultipartParsing="true" 추가
        // <Context docBase="" path="/" reloadable="true" allowCasualMultipartParsing="true">
        //-------------------------------------------------------------
        MultipartFilter springMultipartFilter = new MultipartFilter();
        springMultipartFilter.setMultipartResolverBeanName("multipartResolver");
        FilterRegistration.Dynamic multipartFilter = servletContext.addFilter("springMultipartFilter", springMultipartFilter);
        multipartFilter.addMappingForUrlPatterns(null, false, "*.do");

		// -------------------------------------------------------------
		// Spring Root Context 설정
		// -------------------------------------------------------------
		addRootContext(servletContext);

		// -------------------------------------------------------------
		// Spring Servlet Context 설정
		// -------------------------------------------------------------
		addWebServletContext(servletContext);

		// -------------------------------------------------------------
		// 필터설정
		// -------------------------------------------------------------
		addFilters(servletContext);
		
		//-------------------------------------------------------------
		// Spring RequestContextListener 설정
		//-------------------------------------------------------------
		servletContext.addListener(new org.springframework.web.context.request.RequestContextListener());

		log.info("WebfficeWebApplicationInitializer END-============================================");
	}

	/**
	 * @param servletContext
	 * Root Context를 등록한다.
	 */
	private void addRootContext(ServletContext servletContext) {
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(WebfficeConfigApp.class);

		servletContext.addListener(new ContextLoaderListener(rootContext));
	}

	/**
	 * @param servletContext
	 * 필터들을 등록 한다.
	 */
	private void addFilters(ServletContext servletContext) {
		addEncodingFilter(servletContext);
		addCORSFilter(servletContext);
	}

	/**
	 * @param servletContext
	 * Spring CharacterEncodingFilter 설정
	 */
	private void addEncodingFilter(ServletContext servletContext) {
		FilterRegistration.Dynamic characterEncoding = servletContext.addFilter("encodingFilter",
			new org.springframework.web.filter.CharacterEncodingFilter());
		characterEncoding.setInitParameter("encoding", "UTF-8");
		characterEncoding.setInitParameter("forceEncoding", "true");
		characterEncoding.addMappingForUrlPatterns(null, false, "*.do");
	}
	
	/**
	 * @param servletContext
	 * Servlet Context를 등록한다.
	 */

	private void addWebServletContext(ServletContext servletContext) {
		AnnotationConfigWebApplicationContext webApplicationContext = new
		AnnotationConfigWebApplicationContext();
		webApplicationContext.register(WebfficeConfigWebDispatcherServlet.class);
		
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet("action",
		new DispatcherServlet(webApplicationContext));
		dispatcher.setLoadOnStartup(1);
		
		dispatcher.addMapping("*.do"); 
	}
	
	/**
	 * @param servletContext
	 * CORSFilter 설정
	 */
	private void addCORSFilter(ServletContext servletContext) {
		FilterRegistration.Dynamic corsFilter = servletContext.addFilter("SimpleCORSFilter",
			new SimpleCORSFilter());
		corsFilter.addMappingForUrlPatterns(null, false, "*.do");
	}

}
