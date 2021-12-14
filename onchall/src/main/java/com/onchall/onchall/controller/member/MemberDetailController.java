package com.onchall.onchall.controller.member;

import com.onchall.onchall.argumentResolver.Login;
import com.onchall.onchall.dto.Download;
import com.onchall.onchall.dto.MemberDetail;
import com.onchall.onchall.dto.OrderSimple;
import com.onchall.onchall.dto.PointDetail;
import com.onchall.onchall.entity.Member;
import com.onchall.onchall.entity.Order;
import com.onchall.onchall.entity.Point;
import com.onchall.onchall.entity.Purchased;
import com.onchall.onchall.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberDetailController {
    private final MemberService memberService;

    @Transactional(readOnly = true)
    @GetMapping("/memberDetail/download")
    public String downloadList(@Login Member loginMember, Model model) {
        if (loginMember == null) { //세션에 없는 사용자.
            return "redirect:/login";
        }

        List<Download> downloadList = new ArrayList<>();
        List<Purchased> purchased = memberService.getPurchased(loginMember.getId());
        purchased.forEach(e ->
                downloadList.add(new Download(e.getItemName(), e.getExpiryDate(), e.getFileData().getStoreName(), e.getId())));

        //로그인된 회원
        model.addAttribute("member",
                new MemberDetail(loginMember.getName(), loginMember.getEmail(), loginMember.getPoint()));

        //다운로드 목록
        model.addAttribute("downloadList", downloadList);
        return "member/detail/downloadList";
    }

    @Transactional(readOnly = true)
    @GetMapping("/memberDetail/point")
    public String pointDetailList(@Login Member loginMember, Model model) {
        if (loginMember == null) { //세션에 없는 사용자.
            return "redirect:/login";
        }
        List<PointDetail> pointDetails = new ArrayList<>();
        List<Point> pointHistory = memberService.getPointHistory(loginMember.getId());
        pointHistory.forEach(e -> {
            pointDetails.add(new PointDetail(e.getDescription(), e.getDateTime(), e.getValue()));
        });

        //로그인된 회원
        model.addAttribute("member",
                new MemberDetail(loginMember.getName(), loginMember.getEmail(), loginMember.getPoint()));

        //다운로드 목록
        model.addAttribute("pointList", pointDetails);
        return "member/detail/pointDetail";
    }

    @Transactional(readOnly = true)
    @GetMapping("/memberDetail/order")
    public String orderList(@Login Member loginMember, Model model) {
        if (loginMember == null) { //세션에 없는 사용자.
            return "redirect:/login";
        }

        List<OrderSimple> orderSimpleList = new ArrayList<>();
        List<Order> orders = memberService.getOrder(loginMember.getId());
        orders.forEach(e ->
                orderSimpleList.add(new OrderSimple(e.getId(), e.getRepreItemName(), e.getOrderItemCount() - 1, e.getOrderDate(), e.getTotalPrice())));

        model.addAttribute("member",
                new MemberDetail(loginMember.getName(), loginMember.getEmail(), loginMember.getPoint()));

        model.addAttribute("orderList", orderSimpleList);
        return "member/detail/orderList";
    }
}
