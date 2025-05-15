package com.jmk.mapper;

import com.jmk.vo.member.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

	//회원가입
	public void memberJoin(Member member);
	
	//아이디 중복 검사
	public int idCheck(String memberId);
	
	 /* 로그인 */
    public Member memberLogin(Member member);
	
}
