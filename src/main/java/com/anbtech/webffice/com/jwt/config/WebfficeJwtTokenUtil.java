package com.anbtech.webffice.com.jwt.config;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.anbtech.webffice.com.vo.LoginVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

//security 관련 제외한 jwt util 클래스

@Component
public class WebfficeJwtTokenUtil implements Serializable{

	public static final long JWT_TOKEN_VALIDITY = (long) ((1 * 60 * 60) / 60) * 60; //토큰의 유효시간 설정, 기본 60분
	
	@Value("egovframe")
    private String secret;
	
	//retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }
    
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
	
    //for retrieveing any information from token we will need the secret key
    public Claims getAllClaimsFromToken(String token) {
    	System.out.println("===>>> secret = "+secret);
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
    
    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
    
    //generate token for user
    public String generateToken(LoginVO loginVO) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, loginVO.getId());
    }

    public String generateToken(LoginVO loginVO, Map<String, Object> claims) {
        return doGenerateToken(claims, loginVO.getId());
    }
    
    private String doGenerateToken(Map<String, Object> claims, String subject) {
    	System.out.println("===>>> secret = "+secret);
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
            .signWith(SignatureAlgorithm.HS512, secret).compact();
    }
    
    //validate token
    public Boolean validateToken(String token, LoginVO loginVO) {
        final String username = getUsernameFromToken(token);
        return (username.equals(loginVO.getId()) && !isTokenExpired(token));
    }

}
