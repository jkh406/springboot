package com.anbtech.webffice.com.interceptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

import com.anbtech.webffice.com.util.WebfficeUserDetailsHelper;
import com.anbtech.webffice.com.vo.LoginVO;

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

public class AuthenticInterceptor extends WebContentInterceptor {
	
	private final Logger log = LoggerFactory.getLogger(AuthenticInterceptor.class);

	/**
	 * 세션에 계정정보(LoginVO)가 있는지 여부로 인증 여부를 체크한다.
	 * 계정정보(LoginVO)가 없다면, 로그인 페이지로 이동한다.
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {

		LoginVO loginVO = (LoginVO) WebfficeUserDetailsHelper.getAuthenticatedUser();

		if (loginVO.getId() != null) {
			System.out.print("AuthenticInterceptor sessionID"+loginVO.getId());
			
			log.debug("AuthenticInterceptor sessionID "+loginVO.getId());
			log.debug("AuthenticInterceptor ================== ");
			
			return true;
		} else {
			
			log.debug("AuthenticInterceptor Fail!!!!!!!!!!!!================== ");
			
			ModelAndView modelAndView = new ModelAndView("redirect:http://localhost:3000/login");
			throw new ModelAndViewDefiningException(modelAndView);
		}
	}

}
