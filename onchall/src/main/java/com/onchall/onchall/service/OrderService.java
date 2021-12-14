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
    private final CartItemRepository cartItemRepository;
    private final PurchasedRepository purchasedRepository;
    private final CartRepository cartRepository;


    public Order findById(Long orderId) {
        return orderRepository.getById(orderId);
    }

    @Transactional
    public Order addOrder(Member member, Integer usePoint, String payMethod) {
        //초기화
        member.getName();
        List<Purchased> purchasedList = member.getPurchasedList();
        purchasedList.size();

        Cart cart = member.getCart();
        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());
        List<Item> items = cartItems.stream().map(CartItem::getItem).collect(Collectors.toList());
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
        if(!member.getPurchasedList().isEmpty()){
            member.getPurchasedList().get(0);
        }

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
            purchasedList.add(purchased);
        }

        saveOrder.setTotalPrice(totalPrice);

        for (CartItem cartItem : cartItems) {
            cartItemRepository.delete(cartItem);
        }
        cartItems.clear();

        return saveOrder;
    }
}
