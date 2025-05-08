package com.jmk.mapper;

import com.jmk.model.MemberVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

	//회원가입
	public void memberJoin(MemberVO member);
	
	//아이디 중복 검사
	public int idCheck(String memberId);
	
	 /* 로그인 */
    public MemberVO memberLogin(MemberVO member);
	
}
