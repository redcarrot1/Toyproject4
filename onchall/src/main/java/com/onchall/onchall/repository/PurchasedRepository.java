package com.onchall.onchall.repository;

import com.onchall.onchall.entity.Purchased;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchasedRepository extends JpaRepository<Purchased, Long> {
}
