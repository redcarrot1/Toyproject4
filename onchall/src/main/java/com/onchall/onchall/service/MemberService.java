package com.onchall.onchall.service;

import com.onchall.onchall.entity.Member;
import com.onchall.onchall.entity.MemberState;
import com.onchall.onchall.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    public Member login(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("email 이 존재하지 않습니다."));
        if(member.getState()== MemberState.TEMPORARY) throw new IllegalArgumentException("임시 회원 email 입니다.");
        if(!passwordEncoder.matches(password, member.getPassword())) throw new IllegalArgumentException("패스워드가 일치하지 않습니다.");
        return member;
    }

    public Member signup(String name, String email, String password){
        Member member = new Member(name, email, passwordEncoder.encode(password));
        member.setState(MemberState.TEMPORARY);
        return memberRepository.save(member);
    }
}
