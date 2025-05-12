package com.jmk.model;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
public class Member {
	
	//회원 id
	private String memberId;
	
	//회원비밀번호
	private String memberPw;

	//회원 이름
	private String memberName;
	
	//회원 우편번호
	private String memberMail;
	
	//회원주소
	private String memberAddr1;
	
	//회원주소
	private String memberAddr2;
	
	//회원 상세주조
	private String memberAddr3;
	
	// 관리자 구분(0 : 일반 사용자, 1:관리자)
	private int adminCk;

	//회원 돈
	private int money;

	//등록일자
	private  Timestamp createdDt;

	//수정일자
	private  Timestamp modifiedDt;

}
