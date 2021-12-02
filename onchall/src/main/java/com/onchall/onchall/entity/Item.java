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
    @Column(name = "item_id")
    private Long id;

    private String name;
    private Integer price;
    private Integer originPrice;
    private String storeImageName;

    @Lob
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_data_id")
    private FileData fileData;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public void setCategory(Category category){
        this.category = category;
        category.getItems().add(this);
    }

    public void setFileData(FileData fileData){
        this.fileData=fileData;
    }

    public Item(String name, Integer price, Integer originPrice, String storeImageName, String description) {
        this.name = name;
        this.price = price;
        this.originPrice = originPrice;
        this.storeImageName = storeImageName;
        this.description = description;
    }
}
