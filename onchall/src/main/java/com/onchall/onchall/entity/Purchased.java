package com.onchall.onchall.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Purchased {
    @Id
    @GeneratedValue
    @Column(name="purchased_id")
    private Long id;

    private String itemName;
    private LocalDateTime expiryDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="file_data_id")
    private FileData fileData;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    public Purchased(String itemName, LocalDateTime expiryDate, FileData fileData, Member member) {
        this.itemName = itemName;
        this.expiryDate = expiryDate;
        this.fileData = fileData;
        this.member = member;
    }
}
