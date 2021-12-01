package com.onchall.onchall.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Item {
    @Id
    @GeneratedValue
    @Column(name="item_id")
    private Long id;

    private String name;
    private Integer price;
    private Integer originPrice;
    private String storeImageName;

    @Lob
    private String description;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="file_data_id")
    private FileData fileData;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments= new ArrayList<>();
}
