/*
 * **********************************
 * 파일명 : SessionService.java
 * 작성일(수정일) : 2024-08-29
 * 작성자 : 김찬빈
 * -
 * 파일 역할
 * 1. Login & Logout 로직을 담당합니다.
 * **********************************
 */

package com.youngcamp.server.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

  // 로그인 메서드
  public boolean login(String id, String password) {
    boolean isSuccess = false;

    // DB 조회부가 들어가야 함, 지금은 테스트용으로 root root 고정 (2024-08-29)
    isSuccess = "root".equals(id) && "root".equals(password);

    return isSuccess;
  }

  // 로그아웃 메서드
  public void logout(HttpSession session) {
    session.invalidate();
  }
}
