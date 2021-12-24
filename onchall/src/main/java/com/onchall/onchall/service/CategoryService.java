package com.onchall.onchall.service;

import com.onchall.onchall.dto.CategoryIdAndName;
import com.onchall.onchall.entity.Category;
import com.onchall.onchall.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<String> getCategoryNameList(){
        return categoryRepository.findAll().stream()
                .map(Category::getName)
                .collect(Collectors.toList());
    }

    public List<CategoryIdAndName> getCategoryIdAndName(){
        return categoryRepository.findAll().stream()
                .map(e-> new CategoryIdAndName(e.getId(), e.getName()))
                .collect(Collectors.toList());
    }

    public Category addCategory(String name){
        Category category = new Category();
        category.setName(name);
        return categoryRepository.save(category);
    }

    public Category getCategoryByName(String name){
        return categoryRepository.findByName(name)
                .orElseThrow(()->new IllegalArgumentException("존재하는 카테고리가 없습니다."));
    }
    public Category getCategoryById(Long id){
        return categoryRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("존재하는 카테고리가 없습니다."));
    }

}
