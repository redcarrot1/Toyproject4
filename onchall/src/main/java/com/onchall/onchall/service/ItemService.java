package com.onchall.onchall.service;

import com.onchall.onchall.dto.BoardItemDto;
import com.onchall.onchall.dto.CommentDto;
import com.onchall.onchall.dto.ItemDetail;
import com.onchall.onchall.dto.Pagination;
import com.onchall.onchall.entity.Category;
import com.onchall.onchall.entity.FileData;
import com.onchall.onchall.entity.Item;
import com.onchall.onchall.repository.CategoryRepository;
import com.onchall.onchall.repository.FileDataRepository;
import com.onchall.onchall.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final CategoryService categoryService;
    private final FileDataService fileDataService;

    @Value("${store.path.image}")
    private String imageStorePath;

    @Value("${store.path.file}")
    private String fileStorePath;

    @Value("${page.showItemNumberByPage}")
    private Integer showItemNumberByPage;

    @Value("${page.showPageNumberByPage}")
    private Integer showPageNumberByPage;

    public Pagination sortByCategoryAndPage(String sort, String categoryName, Integer pageNumber) {
        PageRequest pageRequest = PageRequest.of(pageNumber, showItemNumberByPage);
        //PageRequest pageRequest = PageRequest.of(page, 12, Sort.by(Sort.Direction.DESC, "username"));

        Page<Item> page;
        if (categoryName.equals("all")) page = itemRepository.findAll(pageRequest);
        else page = itemRepository.findByCategory(categoryService.getCategoryByName(categoryName), pageRequest);

        List<BoardItemDto> itemList = new ArrayList<>();
        page.getContent().forEach(e ->
                itemList.add(new BoardItemDto(e.getName(), e.getStoreImageName(), e.getOriginPrice(), e.getPrice(), e.getId())));

        Integer start = (pageNumber / showPageNumberByPage) * showPageNumberByPage;
        Integer end = start + showPageNumberByPage - 1;
        boolean isFirst = page.isFirst();
        boolean isEnd = !page.hasNext();
        Integer totalPageNumber = page.getTotalPages();

        return new Pagination(start, end, isFirst, isEnd, pageNumber, totalPageNumber, categoryName, sort, itemList);
    }

    public ItemDetail getItemDetail(Long itemId) {
        Item item = itemRepository.getById(itemId);
        List<CommentDto> commentDtoList = new ArrayList<>();
        item.getComments().forEach(e -> {
            commentDtoList.add(new CommentDto(e.getMember().getName(), e.getRating(), e.getContent()));
        });

        return new ItemDetail(item.getId(), item.getName(), item.getPrice(), item.getOriginPrice(),
                item.getDescription(), commentDtoList, item.getStoreImageName(), item.getCategory().getId(), item.getCategory().getName());
    }

    public List<BoardItemDto> getItem5ByCategory(Long categoryId) {
        Category category = categoryService.getCategoryById(categoryId);
        List<Item> items = itemRepository.findFirst5ByCategory(category);
        return items.stream()
                .map(e -> new BoardItemDto(e.getName(), e.getStoreImageName(), e.getOriginPrice(), e.getPrice(), e.getId()))
                .collect(Collectors.toList());
    }

    public Item getItemById(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("아이템이 없습니다."));
    }

    public Item addItem(String itemName, Integer price, Integer originPrice, String description,
                        Long categoryId, MultipartFile image, MultipartFile fileData) throws IOException {
        Category category = categoryService.getCategoryById(categoryId);

        String imageFullName = createStoreFileName(image.getOriginalFilename());
        image.transferTo(new File(imageStorePath + imageFullName));

        String fileFullName = createStoreFileName(fileData.getOriginalFilename());
        fileData.transferTo(new File(fileStorePath + fileFullName));
        FileData saveFileData = fileDataService.save(new FileData(fileData.getOriginalFilename(), fileFullName));

        Item item = new Item(itemName, price, originPrice, imageFullName, description);

        item.setCategory(category);
        item.setFileData(saveFileData);
        return itemRepository.save(item);
    }

    public Item getItemByItemId(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 아이템이 없습니다."));
    }

    private String createStoreFileName(String originalFileName) {
        String ext = extractExt(originalFileName);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String originalFileName) {
        int pos = originalFileName.lastIndexOf(".");
        return originalFileName.substring(pos + 1);
    }
}
