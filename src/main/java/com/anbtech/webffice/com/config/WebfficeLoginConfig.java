package com.anbtech.webffice.com.config;

/**
 * EgovLoginConfig 클래스
 * <Notice>
 * 	    사용자 인증수행제한에 대한 설정을 관리하는 클래스 
 * <Disclaimer>
 *		N/A
 *
 * @author 장동한
 * @since 2023.04.28
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일        수정자           수정내용
 *  -------      -------------  ----------------------
 * </pre>
 */


public class WebfficeLoginConfig {
	//로그인 인증 제한 여부
	boolean lock = false;
	//로그인 인증 제한 횟수
	int lockCount = 0;

	public boolean isLock() {
		return lock;
	}

	public void setLock(boolean lock) {
		this.lock = lock;
	}

	public int getLockCount() {
		return lockCount;
	}

	public void setLockCount(int lockCount) {
		this.lockCount = lockCount;
	}
}
