package com.onchall.onchall.controller;

import com.onchall.onchall.dto.Shop;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    @GetMapping("/health_check")
    @ResponseBody
    public String health_check() {
        return "service is activation";
    }

    @GetMapping("/thymeleaf")
    public String thymeleaf(Model model){
        model.addAttribute("shop", new Shop());
        return "thymeleaf";
    }

    @PostMapping("/thymeleaf")
    public String thymeleafResult(@ModelAttribute Shop shop) {
        log.info("open={}", shop.isOpen());
        return "thymeleaf";
    }
}
