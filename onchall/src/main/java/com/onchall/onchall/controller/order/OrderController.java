package com.onchall.onchall.controller.order;

import com.onchall.onchall.argumentResolver.Login;
import com.onchall.onchall.entity.*;
import com.onchall.onchall.form.OrderForm;
import com.onchall.onchall.form.OrderItemForm;
import com.onchall.onchall.service.CartService;
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

    @GetMapping("/order")
    public String order(@Login Member member, Model model) {
        OrderForm orderForm = new OrderForm();
        orderForm.setMemberId(member.getId());
        orderForm.setMemberEmail(member.getEmail());
        orderForm.setMemberName(member.getName());
        orderForm.setMemberPoint(member.getPoint());
        orderForm.setUsePoint(0);

        Integer totalOrderPrice = 0;
        List<OrderItemForm> orderItemFormList = new ArrayList<>();
        List<CartItem> cartItemList = cartService.getCartItemListByMemberId(member.getId());
        for (CartItem cartItem : cartItemList) {
            Item e = cartItem.getItem();
            orderItemFormList.add(new OrderItemForm(e.getName(), e.getPrice(), e.getId()));
            totalOrderPrice += e.getPrice();
        }

        orderForm.setOrderItemFormList(orderItemFormList);
        orderForm.setTotalOrderPrice(totalOrderPrice);
        model.addAttribute("orderForm", orderForm);
        return "order/order";
    }

    @PostMapping("/order")
    public String order(@Login Member member, Model model, @RequestParam Integer usePoint, @RequestParam String payMethod) {
        Order order = orderService.addOrder(member, usePoint, payMethod);
        model.addAttribute("orderId", order.getId());
        return "order/orderComple";
    }
}
