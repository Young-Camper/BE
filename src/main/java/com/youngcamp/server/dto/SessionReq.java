/*
 * **********************************
 * 파일명 : SessionReq.java
 * 작성일(수정일) : 2024-08-29
 * 작성자 : 김찬빈
 * -
 * 파일 역할
 * 1. Session 로그인 시 사용되는 객체 클래스를 정의하고 있습니다.
 * 2. 단순히 ID와 PW 두개의 변수를 가지고 있습니다.
 * ***********************************
 */

package com.youngcamp.server.dto;

public class SessionReq {
  private final String adminID;
  private final String adminPW;

  public SessionReq(String id, String pw) {
    this.adminID = id;
    this.adminPW = pw;
  }

  public String getID() {
    return adminID;
  }

  public String getPassword() {
    return adminPW;
  }
}
