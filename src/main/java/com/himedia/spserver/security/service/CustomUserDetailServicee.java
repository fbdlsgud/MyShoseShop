package com.himedia.spserver.security.service;

import com.himedia.spserver.dto.MemberDTO;
import com.himedia.spserver.entity.Member;
import com.himedia.spserver.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomUserDetailServicee implements UserDetailsService {

    final MemberRepository mR;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername - username : " + username + "   ----------------------------------");

        Member member = mR.getWithRoles( username );
        if( member == null ){
            throw new UsernameNotFoundException(username + " - User Not found");
        }
        MemberDTO memberdto = new MemberDTO(
                member.getUserid(),
                member.getPwd(),
                member.getName(),
                member.getEmail(),
                member.getPhone(),
                member.getZip_num(),
                member.getAddress1(),
                member.getAddress2(),
                member.getAddress3(),
                //member.getIndate(),
                member.getProvider(),
                member.getSnsid(),
                member.getMemberRoleList().stream().map(memberRole -> memberRole.name()).collect(Collectors.toList())
        );
        System.out.println("member : " + member);
        System.out.println("memberdto : " + memberdto);
        return memberdto;
        // UsernamePasswordAuthenticationToken 를 거쳐서
        // 로그인에 성공을 하면  APILoginSuccessHandler
        // 로그인에 실패하면   APILoginFailHandler
        // 로 이동합니다
    }
}
