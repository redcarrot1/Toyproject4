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

    public List<OrderItem> findOrderItemsByOrderId(Long orderId){
        return orderItemRepository.findByOrderId(orderId);
    }
}
