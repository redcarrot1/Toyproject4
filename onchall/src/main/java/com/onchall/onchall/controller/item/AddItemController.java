package com.onchall.onchall.controller.item;

import com.onchall.onchall.SessionData;
import com.onchall.onchall.argumentResolver.Login;
import com.onchall.onchall.entity.Item;
import com.onchall.onchall.entity.Member;
import com.onchall.onchall.entity.MemberState;
import com.onchall.onchall.form.AddCategoryForm;
import com.onchall.onchall.form.RegisterForm;
import com.onchall.onchall.service.CategoryService;
import com.onchall.onchall.service.ItemService;
import com.onchall.onchall.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
        return "true";
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
            return "alert/itemAddOnlyAdminAlert";
        }

        model.addAttribute("registerForm", new RegisterForm());
        model.addAttribute("categoryList", categoryService.getCategoryIdAndName());
        return "item/addItem/addItemForm";
    }

    @PostMapping("/item/add")
    public String addItem(@Validated @ModelAttribute RegisterForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            model.addAttribute("categoryList", categoryService.getCategoryIdAndName());
            return "item/addItem/addItemForm";
        }

        try {
            Item item = itemService.addItem(form.getItemName(), form.getPrice(), form.getOriginPrice(), form.getDescription(),
                    form.getCategoryId(), form.getImage(), form.getFileData());
            model.addAttribute("itemId", item.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return "alert/itemAddFailAlert";
        }
        return "item/addItem/addItemComplete";
    }
}
