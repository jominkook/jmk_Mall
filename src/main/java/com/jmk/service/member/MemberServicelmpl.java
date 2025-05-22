package com.jmk.service.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmk.mapper.MemberMapper;
import com.jmk.vo.member.Member;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberServicelmpl implements MemberService {
	
	@Autowired
	private MemberMapper membermapper;

	@Transactional
	@Override
	public void memberJoin(Member member) throws Exception {
		
		membermapper.memberJoin(member);
	}
	
	@Override
	public int idCheck(String memberId) throws Exception {
		
		return membermapper.idCheck(memberId);
	}
	

    /* 로그인 */
	@Transactional
    @Override
    public Member memberLogin(Member member) throws Exception {
        return membermapper.memberLogin(member);
    }


 }
