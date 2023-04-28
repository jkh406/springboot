package com.anbtech.webffice.com.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternUtils;

/**
 *  Class Name : WebfficeProperties.java
 *  Description : properties값들을 파일로부터 읽어와   Globals클래스의 정적변수로 로드시켜주는 클래스로
 *   문자열 정보 기준으로 사용할 전역변수를 시스템 재시작으로 반영할 수 있도록 한다.
 *  Modification Information
 *
 *     수정일         수정자                   수정내용
 *   -------    --------    ---------------------------
 *
 *  @since 2023. 04. 20
 *  @version 1.0
 *  @see
 *
 */

public class WebfficeProperties {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebfficeProperties.class);

	//프로퍼티값 로드시 에러발생하면 반환되는 에러문자열
	public static final String ERR_CODE = " EXCEPTION OCCURRED";
	public static final String ERR_CODE_FNFE = " EXCEPTION(FNFE) OCCURRED";
	public static final String ERR_CODE_IOE = " EXCEPTION(IOE) OCCURRED";

	//파일구분자
	static final String FILE_SEPARATOR = System.getProperty("file.separator");

	// /target/classes/application.properties
	public static final String GLOBALS_PROPERTIES_FILE = "classpath:" + FILE_SEPARATOR + "application.properties";

	/**
	 * 인자로 주어진 문자열을 Key값으로 하는 프로퍼티 값을 반환한다(Globals.java 전용)
	 * @param keyName String
	 * @return String
	*/
	public static String getProperty(String keyName) {
		String value = ERR_CODE;
		value = "99";
		
        Resource resources = ResourcePatternUtils.getResourcePatternResolver(new DefaultResourceLoader())
			    .getResource(GLOBALS_PROPERTIES_FILE);
		
        debug(GLOBALS_PROPERTIES_FILE + " : " + keyName);
		
		try (InputStream in = resources.getInputStream()) {
			Properties props = new Properties(); 
			props.load(new java.io.BufferedInputStream(in));
			value = props.getProperty(keyName).trim();
		} catch (FileNotFoundException fne) {
			debug(fne);
		} catch (IOException ioe) {
			debug(ioe);
		}
		return value;
	}
	

	/**
	 * 시스템 로그를 출력한다.
	 * @param obj Object
	 */
	private static void debug(Object obj) {
		if (obj instanceof java.lang.Exception) {
			LOGGER.debug("IGNORED: {}", ((Exception)obj).getMessage());
		}
	}
}
