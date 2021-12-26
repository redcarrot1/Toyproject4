package com.onchall.onchall.controller.order;

import com.onchall.onchall.SessionData;
import com.onchall.onchall.argumentResolver.Login;
import com.onchall.onchall.entity.*;
import com.onchall.onchall.form.OrderForm;
import com.onchall.onchall.form.OrderItemForm;
import com.onchall.onchall.service.CartService;
import com.onchall.onchall.service.MemberService;
import com.onchall.onchall.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final CartService cartService;
    private final MemberService memberService;

    @GetMapping("/order")
    public String order(@Login SessionData loginMemberId, Model model) {
        Member loginMember = memberService.getMemberByMemberId(loginMemberId.getMemberId());
        OrderForm orderForm = new OrderForm();
        orderForm.setMemberId(loginMember.getId());
        orderForm.setMemberEmail(loginMember.getEmail());
        orderForm.setMemberName(loginMember.getName());
        orderForm.setMemberPoint(loginMember.getPoint());
        orderForm.setUsePoint(0);

        Integer totalOrderPrice = 0;
        List<OrderItemForm> orderItemFormList = new ArrayList<>();
        List<CartItem> cartItemList = cartService.getCartItemListByMemberId(loginMember.getId());
        for (CartItem cartItem : cartItemList) {
            Item item = cartItem.getItem();
            orderItemFormList.add(new OrderItemForm(item.getName(), item.getPrice(), item.getId()));
            totalOrderPrice += item.getPrice();
        }

        orderForm.setOrderItemFormList(orderItemFormList);
        orderForm.setTotalOrderPrice(totalOrderPrice);
        model.addAttribute("orderForm", orderForm);
        return "order/order";
    }

    @PostMapping("/order")
    public String order(@Login SessionData loginMemberId, Model model, @RequestParam Integer usePoint, @RequestParam String payMethod) {
        Member loginMember = memberService.getMemberByMemberId(loginMemberId.getMemberId());
        Order order = orderService.addOrder(loginMember, usePoint, payMethod);
        model.addAttribute("orderId", order.getId());
        return "order/orderComple";
    }
}
