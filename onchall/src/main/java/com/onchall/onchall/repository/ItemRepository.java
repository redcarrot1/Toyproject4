package com.onchall.onchall.repository;

import com.onchall.onchall.entity.Category;
import com.onchall.onchall.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Page<Item> findAll(Pageable pageable); //count 쿼리 사용

    List<Item> findFirst5ByCategory(Category category);

    Page<Item> findByCategory(Category category, Pageable pageable);

}
