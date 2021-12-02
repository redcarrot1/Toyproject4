package com.onchall.onchall.service;

import com.onchall.onchall.dto.BoardItemDto;
import com.onchall.onchall.dto.Pagination;
import com.onchall.onchall.entity.Item;
import com.onchall.onchall.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public Pagination sortByCategoryAndPage(String sort, String category, Integer pageNumber) {
        PageRequest pageRequest = PageRequest.of(pageNumber, 12);
        //PageRequest pageRequest = PageRequest.of(page, 12, Sort.by(Sort.Direction.DESC, "username"));
        Page<Item> page = itemRepository.findAll(pageRequest);
        //Page<Item> page = itemRepository.findByAge(10, pageRequest);
        List<Item> content = page.getContent(); //조회된 데이터
        List<BoardItemDto> itemList = new ArrayList<>();
        content.forEach(e->{
            itemList.add(new BoardItemDto(e.getName(), e.getStoreImageName(), e.getOriginPrice(), e.getPrice()));
        });

        Integer start=(pageNumber/5)*5; //한 페이지당 최대 5개씩 페이지 번호 보여주기
        Integer end=start+5-1;
        boolean isFirst = page.isFirst();
        boolean isEnd = !page.hasNext();
        Integer totalPageNumber = page.getTotalPages();

        return new Pagination(start, end, isFirst, isEnd, pageNumber, totalPageNumber, itemList);
    }
}
