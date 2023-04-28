package com.anbtech.webffice.com.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 인증여부 체크 인터셉터
 * @since 2023.04.20
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  </pre>
 */


public class CustomAuthenticInterceptor extends HandlerInterceptorAdapter {

	private final Logger log = LoggerFactory.getLogger(CustomAuthenticInterceptor.class);
	
	/**
	 * 세션에 계정정보(LoginVO)가 있는지 여부로 인증 여부를 체크한다.
	 * 계정정보(LoginVO)가 없다면, 로그인 페이지로 이동한다.
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		HttpSession session = request.getSession();
		log.debug("CustomAuthenticInterceptor sessionID "+session.getId());
		log.debug("CustomAuthenticInterceptor ================== ");
		
		boolean isPermittedURL = true;
		return isPermittedURL;
	}

}
