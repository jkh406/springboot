package com.anbtech.webffice.com.util;

import java.util.List;

import com.anbtech.webffice.com.service.WebfficeUserDetailsService;

/**
 * WebfficeUserDetails Helper 클래스
 *
 * @since 2023.04.20
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 *
 * </pre>
 */

public class WebfficeUserDetailsHelper {

	static WebfficeUserDetailsService webfficeUserDetailsService;

	public WebfficeUserDetailsService getEgovUserDetailsService() {
		return webfficeUserDetailsService;
	}

	public void setWebfficeUserDetailsService(WebfficeUserDetailsService webfficeUserDetailsService) {
		WebfficeUserDetailsHelper.webfficeUserDetailsService = webfficeUserDetailsService;
	}

	/**
	 * 인증된 사용자객체를 VO형식으로 가져온다.
	 * @return Object - 사용자 ValueObject
	 */
	public static Object getAuthenticatedUser() {
		return webfficeUserDetailsService.getAuthenticatedUser();
	}

	/**
	 * 인증된 사용자의 권한 정보를 가져온다.
	 *
	 * @return List - 사용자 권한정보 목록
	 */
	public static List<String> getAuthorities() {
		return webfficeUserDetailsService.getAuthorities();
	}

	/**
	 * 인증된 사용자 여부를 체크한다.
	 * @return Boolean - 인증된 사용자 여부(TRUE / FALSE)
	 */
	public static Boolean isAuthenticated() {
		return webfficeUserDetailsService.isAuthenticated();
	}
}
