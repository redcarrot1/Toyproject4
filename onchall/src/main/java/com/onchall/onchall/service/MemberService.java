package com.onchall.onchall.service;

import com.onchall.onchall.entity.*;
import com.onchall.onchall.repository.CartRepository;
import com.onchall.onchall.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final PasswordEncoder passwordEncoder;


    public Member login(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("email 이 존재하지 않습니다."));
        if(member.getState()== MemberState.TEMPORARY) throw new IllegalArgumentException("임시 회원 email 입니다.");
        if(!passwordEncoder.matches(password, member.getPassword())) throw new IllegalArgumentException("패스워드가 일치하지 않습니다.");
        return member;
    }

    public Member signup(String name, String email, String password){
        Cart cart = new Cart();
        cartRepository.save(cart);

        Member member = new Member(name, email, passwordEncoder.encode(password), cart);
        member.setState(MemberState.TEMPORARY);
        return memberRepository.save(member);
    }

    public void changeMemberState(String email){
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("email 이 존재하지 않습니다."));
        member.setState(MemberState.USER);
        memberRepository.save(member);
    }

    public Member findMemberNameAndEmail(String name, String email) {
        Member member = memberRepository.findByNameAndEmail(name, email)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 회원이 없습니다."));
        if(member.getState()== MemberState.TEMPORARY) throw new IllegalArgumentException("임시 회원 email 입니다.");
        return member;
    }

    public void changeMemberPassword(String email, String newPassword) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("email 이 존재하지 않습니다."));
        member.setPassword(passwordEncoder.encode(newPassword));
        memberRepository.save(member);
    }

    public List<Purchased> getPurchased(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 회원이 없습니다."));
        return member.getPurchasedList();
    }

    public List<Point> getPointHistory(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 회원이 없습니다."));
        return member.getPointHistory();
    }

    public List<Order> getOrder(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 회원이 없습니다."));
        return member.getOrders();
    }
}
