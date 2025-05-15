package com.jmk.service.member;

import com.jmk.vo.member.Member;
import com.jmk.vo.product.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface MemberService {

	//회원가입
	public void memberJoin(Member member) throws Exception;
	
	//아이디 중복 검사
	public int idCheck(String memberId) throws Exception;

    /* 로그인 */
    public Member memberLogin(Member member) throws Exception;


}
