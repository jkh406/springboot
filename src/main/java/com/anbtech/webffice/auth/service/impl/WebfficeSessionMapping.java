package com.anbtech.webffice.auth.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.egovframe.rte.fdl.security.userdetails.EgovUserDetails;
import org.egovframe.rte.fdl.security.userdetails.jdbc.EgovUsersByUsernameMapping;

import com.anbtech.webffice.com.vo.LoginVO;

/**
 * mapRow 결과를 사용자 EgovUserDetails Object 에 정의한다.
 *
 * @since 2023.05.02
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

public class WebfficeSessionMapping extends EgovUsersByUsernameMapping  {

	/**
	 * 사용자정보를 테이블에서 조회하여 EgovUsersByUsernameMapping 에 매핑한다.
	 * @param ds DataSource
	 * @param usersByUsernameQuery String
	 */
	public WebfficeSessionMapping(DataSource ds, String usersByUsernameQuery) {
        super(ds, usersByUsernameQuery);
    }

	/**
	 * mapRow Override
	 * @param rs ResultSet 결과
	 * @param rownum row num
	 * @return Object EgovUserDetails
	 * @exception SQLException
	 */
	@Override
	protected EgovUserDetails mapRow(ResultSet rs, int rownum) throws SQLException {
    	logger.debug("## EgovUsersByUsernameMapping mapRow ##");

        String strUserId    = rs.getString("user_id");
        String strPassWord  = rs.getString("password");
        boolean strEnabled  = rs.getBoolean("enabled");

        String strUserNm    = rs.getString("user_nm");
        String strUserSe    = rs.getString("user_se");
        String strUserEmail = rs.getString("user_email");
        String strOrgnztId  = rs.getString("orgnzt_id");
        String strUniqId    = rs.getString("esntl_id");
        String strOrgnztNm    = rs.getString("orgnzt_nm");



        // 세션 항목 설정
        LoginVO loginVO = new LoginVO();
        loginVO.setId(strUserId);
        loginVO.setPassword(strPassWord);
        loginVO.setName(strUserNm);
        loginVO.setUserSe(strUserSe);
        loginVO.setEmail(strUserEmail);

        return new EgovUserDetails(strUserId, strPassWord, strEnabled, loginVO);
    }
}
