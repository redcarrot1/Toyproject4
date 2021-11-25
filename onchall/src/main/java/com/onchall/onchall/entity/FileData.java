package com.onchall.onchall.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Getter
public class FileData {
    @Id
    @GeneratedValue
    @Column(name="file_id")
    private Long id;

    private String uploadName;
    private String storeName;
}
