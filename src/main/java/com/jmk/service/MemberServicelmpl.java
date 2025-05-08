package com.jmk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmk.mapper.MemberMapper;
import com.jmk.model.MemberVO;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberServicelmpl implements MemberService {
	
	@Autowired
	MemberMapper membermapper;

	@Transactional
	@Override
	public void memberJoin(MemberVO member) throws Exception {
		
		membermapper.memberJoin(member);
	}
	
	@Override
	public int idCheck(String memberId) throws Exception {
		
		return membermapper.idCheck(memberId);
	}
	

    /* 로그인 */
	@Transactional
    @Override
    public MemberVO memberLogin(MemberVO member) throws Exception {
        
        return membermapper.memberLogin(member);
    }
 }
