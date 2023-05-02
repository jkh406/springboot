package com.anbtech.webffice.auth.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.anbtech.webffice.auth.service.WebfficeLoginService;
import com.anbtech.webffice.com.ResponseCode;
import com.anbtech.webffice.com.WebfficeMessageSource;
import com.anbtech.webffice.com.jwt.config.WebfficeJwtTokenUtil;
import com.anbtech.webffice.com.service.WebfficeClntInfo;
import com.anbtech.webffice.com.vo.LoginVO;
import com.anbtech.webffice.com.vo.ResultVO;

/**
 * 일반 로그인을 처리하는 컨트롤러 클래스
 * @since 2023.04.20
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *  수정일        수정자      수정내용
 *  -------    --------   ---------------------------
 *
 *  </pre>
 */
@RestController
public class WebfficeLoginApiController {

	private final Logger log = LoggerFactory.getLogger(WebfficeLoginApiController.class);

	/** WebfficeLoginService */
	@Autowired
	private WebfficeLoginService loginService;
	

	/** WebfficeMessageSource */
	@Autowired
	WebfficeMessageSource webfficeMessageSource;
	
	/** JWT */
	@Autowired
    private WebfficeJwtTokenUtil jwtTokenUtil;
	
	/**
	 * 일반(스프링 시큐리티) 로그인을 처리한다
	 * @param vo - 아이디, 비밀번호가 담긴 LoginVO
	 * @param request - 세션처리를 위한 HttpServletRequest
	 * @return result - 로그인결과(세션정보)
	 * @exception Exception
	 */
	@PostMapping(value = "/uat/uia/actionSecurityLogin.do")
	public HashMap<String, Object> actionSecurityLogin(@RequestBody LoginVO loginVO, HttpServletResponse response, HttpServletRequest request) throws Exception {

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		System.out.print("로그인 테스트");
		log.info("===>>> 로그인 테스트 = ");
		
		// 1. 일반 로그인 처리
		LoginVO resultVO = loginService.actionLogin(loginVO);
		log.info("===>>> 로그인 테스트 resultVO= " + resultVO);
		
		if (resultVO != null && resultVO.getId() != null && !resultVO.getId().equals("")) {

			System.out.println("===>>> loginVO.getUserSe() = "+loginVO.getUserSe());
			System.out.println("===>>> loginVO.getId() = "+loginVO.getId());
			System.out.println("===>>> loginVO.getPassword() = "+loginVO.getPassword());
			
			// 2. spring security, jwt 연동
			request.getSession().setAttribute("LoginVO", resultVO);

			UsernamePasswordAuthenticationFilter springSecurity = null;

			ApplicationContext act = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());

			Map<String, UsernamePasswordAuthenticationFilter> beans = act.getBeansOfType(UsernamePasswordAuthenticationFilter.class);

			System.out.println("===>>> loginVO.beans = "+beans);
			if (beans.size() > 0) {
				
				springSecurity = (UsernamePasswordAuthenticationFilter) beans.values().toArray()[0];
				springSecurity.setUsernameParameter("webffice_security_username");
				springSecurity.setPasswordParameter("webffice_security_password");
				springSecurity.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(request.getServletContext().getContextPath() +"/webffice_security_login", "POST"));
				
			} else {
				throw new IllegalStateException("No AuthenticationProcessingFilter");
			}
			
			springSecurity.doFilter(new RequestWrapperForSecurity(request, resultVO.getUserSe()+ resultVO.getId(), resultVO.getEmp_no()), response, null);

			String jwtToken = jwtTokenUtil.generateToken(loginVO);
	    	request.getSession().setAttribute("LoginVO", resultVO);
			String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
	    	System.out.println("Dec jwtToken username = "+username);
	    	
			resultMap.put("resultVO", resultVO);
			resultMap.put("jToken", jwtToken);
			resultMap.put("resultCode", "200");
			resultMap.put("resultMessage", "성공 !!!");
			return resultMap; // 성공 시 페이지.. (redirect 불가)

		} else {
			resultMap.put("resultVO", resultVO);
			resultMap.put("resultCode", "300");
			resultMap.put("resultMessage", "실패 !!!");

			return resultMap;
		}
	}
	
	@PostMapping(value = "/uat/uia/actionLoginJWT.do")
	public HashMap<String, Object> actionLoginJWT(@RequestBody LoginVO loginVO, HttpServletRequest request) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		// 1. 일반 로그인 처리
		LoginVO loginResultVO = loginService.actionLogin(loginVO);
		
		if (loginResultVO != null && loginResultVO.getId() != null && !loginResultVO.getId().equals("")) {

			System.out.println("===>>> loginVO.getUserSe() = "+loginVO.getUserSe());
			System.out.println("===>>> loginVO.getId() = "+loginVO.getId());
			System.out.println("===>>> loginVO.getPassword() = "+loginVO.getPassword());
			
			String jwtToken = jwtTokenUtil.generateToken(loginVO);
			
			String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
	    	System.out.println("Dec jwtToken username = "+username);
	    	 
	    	//서버사이드 권한 체크 통과를 위해 삽입
	    	request.getSession().setAttribute("LoginVO", loginResultVO);
	    	
			resultMap.put("resultVO", loginResultVO);
			resultMap.put("jToken", jwtToken);
			resultMap.put("resultCode", "200");
			resultMap.put("resultMessage", "성공 !!!");
			
		} else {
			resultMap.put("resultVO", loginResultVO);
			resultMap.put("resultCode", "300");
			resultMap.put("resultMessage", "실패 !!!");
		}
		
		return resultMap;
	}

	/**
	 * 로그아웃한다.
	 * @return resultVO
	 * @exception Exception
	 */
	@GetMapping(value = "/uat/uia/actionLogoutAPI.do")
	public ResultVO actionLogoutJSON(HttpServletRequest request) throws Exception {
		ResultVO resultVO = new ResultVO();

		RequestContextHolder.currentRequestAttributes().removeAttribute("LoginVO", RequestAttributes.SCOPE_SESSION);

		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());

		return resultVO;
	}
	

class RequestWrapperForSecurity extends HttpServletRequestWrapper {
	private String username = null;
	private String password = null;

	public RequestWrapperForSecurity(HttpServletRequest request, String username, String password) {
		super(request);

		this.username = username;
		this.password = password;
	}
	
	@Override
	public String getServletPath() {		
		return ((HttpServletRequest) super.getRequest()).getContextPath() + "/webffice_security_login";
	}

	@Override
	public String getRequestURI() {		
		return ((HttpServletRequest) super.getRequest()).getContextPath() + "/webffice_security_login";
	}

	@Override
	public String getParameter(String name) {
		if (name.equals("webffice_security_username")) {
			return username;
		}

		if (name.equals("webffice_security_password")) {
			return password;
		}

		return super.getParameter(name);
	}
}
}