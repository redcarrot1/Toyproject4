package com.onchall.onchall.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Point {
    @Id
    @GeneratedValue
    @Column(name="point_id")
    private Long id;

    @Lob
    private String description;
    private Integer value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    private LocalDateTime dateTime;
}
