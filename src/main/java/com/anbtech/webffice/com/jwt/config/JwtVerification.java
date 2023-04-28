package com.anbtech.webffice.com.jwt.config;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anbtech.webffice.com.vo.LoginVO;
import com.anbtech.webffice.com.util.WebfficeUserDetailsHelper;
import com.anbtech.webffice.com.util.WebfficeStringUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtVerification {
	
	@Autowired
	private WebfficeJwtTokenUtil jwtTokenUtil;
	
	private final Logger log = LoggerFactory.getLogger(JwtVerification.class);
	
	public boolean isVerification(HttpServletRequest request) {
		
		boolean verificationFlag = true;
		
		// step 1. request header에서 토큰을 가져온다.
		String jwtToken = WebfficeStringUtil.isNullToString(request.getHeader("authorization"));
		
		// step 2.비교를 위해 loginVO를 가져옴
		LoginVO loginVO = (LoginVO) WebfficeUserDetailsHelper.getAuthenticatedUser();
		
		// step 3. 토큰에 내용이 있는지 확인 & 토큰 기간이 자났는지를 확인해서 username값을 가져옴
		// Exception 핸들링 추가처리
		String username = null;
		
		try {
	           username = jwtTokenUtil.getUsernameFromToken(jwtToken);
	       	System.out.println("===>>> username = "+username);
	        } catch (IllegalArgumentException e) {
	        	log.debug("Unable to get JWT Token");
	        } catch (ExpiredJwtException e) {
	        	log.debug("JWT Token has expired");
	        } catch (MalformedJwtException e) {
	        	log.debug("JWT strings must contain exactly 2 period characters");
	        } catch (UnsupportedJwtException e) {
	        	log.debug("not support JWT token.");
	        }
		
		log.debug("===>>> username = " + username);
		
		// step 4. 가져온 username이랑 2에서 가져온 loginVO랑 비교해서 같은지 체크 & 이 과정에서 한번 더 기간 체크를 한다.
		if (username == null || !(jwtTokenUtil.validateToken(jwtToken, loginVO))) {
	       	System.out.println("===>>> jwtToken not validate");
			verificationFlag =  false;
			return verificationFlag;
		}
		
		log.debug("jwtToken validated");
		
		return verificationFlag;
	}

}
