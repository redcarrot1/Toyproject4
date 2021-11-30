package com.onchall.onchall.controller;

import com.onchall.onchall.dto.MemberDetail;
import com.onchall.onchall.entity.Member;
import com.onchall.onchall.form.LoginForm;
import com.onchall.onchall.form.SignupForm;
import com.onchall.onchall.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/health_check")
    @ResponseBody
    public String health_check() {
        return "service is activation";
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "member/login/login";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute LoginForm loginForm, BindingResult bindingResult,
                        HttpServletRequest request, Model model) {
        Member loginMember = null;
        try {
            loginMember = memberService.login(loginForm.getEmail(), loginForm.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
        }

        if (bindingResult.hasErrors()) {
            log.warn("로그인 실패. errors={}", bindingResult);
            return "member/login/login";
        }

        HttpSession session = request.getSession();
        session.setAttribute("loginMember", loginMember);

        model.addAttribute("member",
                new MemberDetail(loginMember.getName(), loginMember.getEmail(), loginMember.getPoint()));
        return "member/memberDetail";
    }

    @GetMapping("/signup")
    public String signupForm(Model model){
        model.addAttribute("signupForm", new SignupForm());
        return "member/signup/signup";
    }

    @PostMapping("/signup")
    public String signup(@Validated @ModelAttribute SignupForm signupForm, BindingResult bindingResult){
        Member signupMember = memberService.signup(signupForm.getName(), signupForm.getEmail(), signupForm.getPassword());
        return "member/signup/standby";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session != null) session.invalidate();
        return "member/login/login";
    }
}
