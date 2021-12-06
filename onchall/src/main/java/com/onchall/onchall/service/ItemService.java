package com.onchall.onchall.service;

import com.onchall.onchall.dto.BoardItemDto;
import com.onchall.onchall.dto.CommentDto;
import com.onchall.onchall.dto.ItemDetail;
import com.onchall.onchall.dto.Pagination;
import com.onchall.onchall.entity.Category;
import com.onchall.onchall.entity.Item;
import com.onchall.onchall.repository.CategoryRepository;
import com.onchall.onchall.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    public Pagination sortByCategoryAndPage(String sort, String categoryName, Integer pageNumber) {
        PageRequest pageRequest = PageRequest.of(pageNumber, 12);
        //PageRequest pageRequest = PageRequest.of(page, 12, Sort.by(Sort.Direction.DESC, "username"));

        Page<Item> page;
        if(categoryName.equals("all")) page = itemRepository.findAll(pageRequest);
        else page = itemRepository.findByCategory(categoryRepository.findByName(categoryName).get(), pageRequest);
        List<Item> content = page.getContent(); //조회된 데이터
        List<BoardItemDto> itemList = new ArrayList<>();
        content.forEach(e -> {
            itemList.add(new BoardItemDto(e.getName(), e.getStoreImageName(), e.getOriginPrice(), e.getPrice(), e.getId()));
        });

        Integer start = (pageNumber / 5) * 5; //한 페이지당 최대 5개씩 페이지 번호 보여주기
        Integer end = start + 5 - 1;
        boolean isFirst = page.isFirst();
        boolean isEnd = !page.hasNext();
        Integer totalPageNumber = page.getTotalPages();

        Pagination pagination = new Pagination(start, end, isFirst, isEnd, pageNumber, totalPageNumber, categoryName, sort, itemList);
        return pagination;
    }

    public ItemDetail getItemDetail(Long itemId){
        Item item = itemRepository.getById(itemId);
        List<CommentDto> commentDtoList = new ArrayList<>();
        item.getComments().forEach(e->{
            commentDtoList.add(new CommentDto(e.getMember().getName(), e.getRating(), e.getContent()));
        });

        return new ItemDetail(item.getId(), item.getName(), item.getPrice(), item.getOriginPrice(),
                item.getDescription(), commentDtoList, item.getStoreImageName(), item.getCategory().getId(), item.getCategory().getName());
    }

    public List<BoardItemDto> getItemByCategory(Long categoryId){
        Category category = categoryRepository.findById(categoryId).get();
        List<Item> items = itemRepository.findFirst5ByCategory(category);
        return items.stream()
                .map(e -> new BoardItemDto(e.getName(), e.getStoreImageName(), e.getOriginPrice(), e.getPrice(), e.getId()))
                .collect(Collectors.toList());
    }

    public Item findById(Long itemId){
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("아이템이 없습니다."));
    }
}
