package com.himedia.spserver.service;

import com.himedia.spserver.entity.Member;
import com.himedia.spserver.entity.MemberRole;
import com.himedia.spserver.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MemberService {

    @Autowired
    MemberRepository mR;

    BCryptPasswordEncoder pe = new BCryptPasswordEncoder();

    public Member getMember(String userid) {
        return mR.findByUserid( userid );
    }

    public void insertMember(Member member) {
        // MemberRole 생성
        List<MemberRole> roles = new ArrayList<MemberRole>();
        // 권한 하나 추가
        roles.add(MemberRole.USER);
        // member 객체에 저장
        member.setMemberRoleList(roles);
        // 패스워드 암호화
        member.setPwd( pe.encode(member.getPwd()) );
        // 레코드 추가
        mR.save(member);
    }

    public Member updateMember(Member member) {
        Member updateMember = mR.findByUserid(member.getUserid());
        updateMember.setPwd( pe.encode( member.getPwd() ));
        updateMember.setName(member.getName());
        updateMember.setEmail(member.getEmail());
        updateMember.setPhone(member.getPhone());
        updateMember.setZip_num(member.getZip_num());
        updateMember.setAddress1(member.getAddress1());
        updateMember.setAddress2(member.getAddress2());
        updateMember.setAddress3(member.getAddress3());
        return updateMember;
    }

    public void deleteMember(String userid) {
        Member member = mR.findByUserid(userid);
        mR.delete(member);
    }
}
