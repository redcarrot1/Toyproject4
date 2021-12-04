package com.onchall.onchall.service;

import com.onchall.onchall.entity.Comment;
import com.onchall.onchall.repository.CommentRepository;
import com.onchall.onchall.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public void save(Comment comment) {
        commentRepository.save(comment);
    }
}
