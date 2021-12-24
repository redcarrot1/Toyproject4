package com.onchall.onchall.service;


import com.onchall.onchall.entity.*;
import com.onchall.onchall.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final PurchasedRepository purchasedRepository;
    private final PointRepository pointRepository;
    private final CartService cartService;
    private final MemberService MemberService;

    @Transactional
    public Order addOrder(Member loginMember, Integer usePoint, String payMethod) {
        Member member = MemberService.getMemberByMemberId(loginMember.getId());

        List<CartItem> cartItems = cartService.getCartItemListByMemberId(member.getId());
        List<Item> items = cartItems.stream()
                .map(CartItem::getItem)
                .collect(Collectors.toList());

        List<OrderItem> orderItems = new ArrayList<>();
        Integer totalPrice = 0;

        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setOrderItems(orderItems);
        order.setOrderItemCount(cartItems.size());
        order.setRepreItemName(items.get(0).getName());
        order.setMember(member);
        order.setUsePoint(usePoint);
        if (payMethod.equals("Card")) order.setPayMethod(PayMethod.Card);
        else order.setPayMethod(PayMethod.BankBook);

        Order saveOrder = orderRepository.save(order);

        for (Item e : items) {
            OrderItem orderItem = new OrderItem();
            orderItem.setItemId(e.getId());
            orderItem.setItemName(e.getName());
            orderItem.setComment(false);
            orderItem.setPrice(e.getPrice());
            orderItem.setOrder(saveOrder);
            totalPrice += e.getPrice();

            OrderItem saveOrderItem = orderItemRepository.save(orderItem);
            saveOrder.getOrderItems().add(saveOrderItem);

            Purchased purchased = purchasedRepository.save(new Purchased(e.getName(), LocalDateTime.now().plusDays(7), e.getFileData(), member));
            member.getPurchasedList().add(purchased);
        }

        saveOrder.setTotalPrice(totalPrice);

        cartItems.forEach(cartService::deleteCartItem);
        cartItems.clear();

        pointRepository.save(new Point("구매 사용", -usePoint, member));
        return saveOrder;
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 order 이 없습니다."));
    }
}
