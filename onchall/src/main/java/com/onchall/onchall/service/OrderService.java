package com.onchall.onchall.service;


import com.onchall.onchall.entity.Order;
import com.onchall.onchall.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;


    public Order findById(Long orderId) {
        return orderRepository.getById(orderId);
    }
}
