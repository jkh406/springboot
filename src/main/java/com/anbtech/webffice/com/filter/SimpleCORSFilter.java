package com.anbtech.webffice.com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anbtech.webffice.com.config.WebfficeProperties;

/**
 * SimpleCORSFilter.java 클래스
 *
 * @since 2023. 04. 20.
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *  수정일                수정자               수정내용
 *  ----------   ----------   ----------------------
 * </pre>
 */
@WebFilter(urlPatterns = "*.do")
public class SimpleCORSFilter implements Filter {

	private final Logger log = LoggerFactory.getLogger(SimpleCORSFilter.class);

	public SimpleCORSFilter() {
		log.info("SimpleCORSFilter init");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
		throws IOException, ServletException {

		log.debug("===>>> SimpleCORSFilter > doFilter()");
		//HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)res;
		String originHeader = WebfficeProperties.getProperty("Globals.Allow.Origin");
		System.out.print("===>>> origin = " + originHeader);
		log.debug("===>>> origin = " + originHeader);

		if (originHeader != null && !originHeader.equals("")) {
			originHeader = originHeader.replace("\r", "").replace("\n", "");
		}
		response.setHeader("Access-Control-Allow-Origin", originHeader);
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
		response.setHeader("Access-Control-Allow-Headers",
			"Origin, X-Requested-With, Content-Type, Accept, Authorization, " + "X-CSRF-TOKEN");

		chain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig filterConfig) {}

	@Override
	public void destroy() {}

}