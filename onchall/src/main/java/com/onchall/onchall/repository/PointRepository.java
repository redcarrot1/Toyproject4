package com.onchall.onchall.repository;

import com.onchall.onchall.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point, Long> {
}
