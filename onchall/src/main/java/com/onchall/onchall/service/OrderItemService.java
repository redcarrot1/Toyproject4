package com.onchall.onchall.service;

import com.onchall.onchall.entity.OrderItem;
import com.onchall.onchall.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;

    public List<OrderItem> findOrderItemsByOrderId(Long orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

    public void setIsCommentTrue(Long orderItemId) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 orderItem 이 없습니다."));
        orderItem.setComment(true);
        orderItemRepository.save(orderItem);
    }
}
