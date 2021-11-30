package com.onchall.onchall.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Controller
@RequiredArgsConstructor
public class TestController {
    @GetMapping("/test")
    public String thymeleaf(Model model){
        return "test";
    }
}
