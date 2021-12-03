package com.onchall.onchall.controller.order;

import com.onchall.onchall.dto.order.OrderDetail;
import com.onchall.onchall.dto.order.OrderItemDetail;
import com.onchall.onchall.entity.Order;
import com.onchall.onchall.service.OrderItemService;
import com.onchall.onchall.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderDetailController {
    private final OrderService orderService;
    private final OrderItemService orderItemService;

    @GetMapping("/orderDetail/{orderId}")
    public String orderDetail(@PathVariable Long orderId, Model model){
        Order order = orderService.findById(orderId);
        List<OrderItemDetail> orderItemDetails =
                orderItemService.findOrderItemsByOrderId(orderId).stream()
                .map(e -> new OrderItemDetail(e.getItemId(), e.getItemId(), e.getItemName(), e.getPrice(), e.isComment()))
                .collect(Collectors.toList());
        OrderDetail orderDetail = new OrderDetail(order.getId(), order.getOrderDate(), order.getUsePoint(),
                order.getTotalPrice(), order.getPayMethod().toString(), orderItemDetails);
        model.addAttribute("orderDetail", orderDetail);
        return "order/orderDetail";
    }
}
