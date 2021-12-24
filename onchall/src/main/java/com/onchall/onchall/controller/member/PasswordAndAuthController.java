package com.onchall.onchall.controller.member;

import com.onchall.onchall.argumentResolver.Login;
import com.onchall.onchall.entity.Member;
import com.onchall.onchall.form.ChangePasswordForm;
import com.onchall.onchall.form.FindPasswordForm;
import com.onchall.onchall.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PasswordAndAuthController {
    private final MemberService memberService;

    @GetMapping("/findPassword")
    public String findPasswordForm(Model model){
        model.addAttribute("findPasswordForm", new FindPasswordForm());
        return "member/password/findPassword";
    }

    @PostMapping("/findPassword")
    public String findPassword(@ModelAttribute FindPasswordForm findPasswordForm, BindingResult bindingResult, Model model){
        Member findMember=null;
        try{
            findMember = memberService.getMemberByNameAndEmail(findPasswordForm.getName(), findPasswordForm.getEmail());
        } catch(Exception e){
            e.printStackTrace();
            bindingResult.reject("findMemberFail", "회원 정보가 없습니다.");
            return "member/password/findPassword";
        }
        //TODO 이메일로 임시 비밀번호 전송
        return "member/password/sendTemplePasswordComple";
    }

    @GetMapping("/changePassword")
    public String changePasswordForm(@Login Member loginMember, Model model){
        if (loginMember == null) { //세션에 없는 사용자.
            return "redirect:/login";
        }
        ChangePasswordForm changePasswordForm = new ChangePasswordForm();
        changePasswordForm.setEmail(loginMember.getEmail());
        model.addAttribute("changePasswordForm", changePasswordForm);
        return "member/password/changePassword";
    }

    @PostMapping("/changePassword")
    public String changePassword(@ModelAttribute ChangePasswordForm changePasswordForm){
        memberService.changeMemberPassword(changePasswordForm.getEmail(), changePasswordForm.getNewPassword());
        return "member/password/changePasswordComple";
    }
}
