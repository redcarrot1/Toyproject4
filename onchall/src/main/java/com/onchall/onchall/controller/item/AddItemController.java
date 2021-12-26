package com.onchall.onchall.controller.item;

import com.onchall.onchall.SessionData;
import com.onchall.onchall.argumentResolver.Login;
import com.onchall.onchall.dto.CategoryIdAndName;
import com.onchall.onchall.entity.*;
import com.onchall.onchall.form.AddCategoryForm;
import com.onchall.onchall.form.RegisterForm;
import com.onchall.onchall.service.CategoryService;
import com.onchall.onchall.service.ItemService;
import com.onchall.onchall.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AddItemController {
    private final ItemService itemService;
    private final CategoryService categoryService;
    private final MemberService memberService;

    @PostMapping("/category/add")
    @ResponseBody
    public String addCategory(@RequestBody AddCategoryForm form) {
        try {
            categoryService.addCategory(form.getName());
            return "true";
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
    }

    @GetMapping("/item/add")
    public String addItemForm(@Login SessionData loginMemberId, Model model) {
        Member loginMember = memberService.getMemberByMemberId(loginMemberId.getMemberId());
        try {
            if (loginMember.getState() != MemberState.ADMIN) {
                throw new IllegalAccessException("관리자만 게시물 등록이 가능합니다.");
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            //todo json 형식으로 받아서 alert 로 처리
            return "member/login/login";
        }

        model.addAttribute("registerForm", new RegisterForm());
        model.addAttribute("categoryList", categoryService.getCategoryIdAndName());
        return "item/addItem/addItemForm";
    }

    @PostMapping("/item/add")
    public String addItem(@ModelAttribute RegisterForm form, Model model) {
        try {
            Item item = itemService.addItem(form.getItemName(), form.getPrice(), form.getOriginPrice(), form.getDescription(),
                    form.getCategoryId(), form.getImage(), form.getFileData());
            model.addAttribute("itemId", item.getId());
        } catch (Exception e) {
            e.printStackTrace();
            //todo 등록 실패
            return "member/login/login";
        }
        return "item/addItem/addItemComplete";
    }
}
