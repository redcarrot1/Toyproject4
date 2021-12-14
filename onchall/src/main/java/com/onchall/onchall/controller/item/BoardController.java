package com.onchall.onchall.controller.item;

import com.onchall.onchall.dto.Pagination;
import com.onchall.onchall.service.CategoryService;
import com.onchall.onchall.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.MalformedURLException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardController {
    private final ItemService itemService;
    private final CategoryService categoryService;

    @Value("${store.path.image}")
    private String imageStorePath;

    @GetMapping("/board/{category}")
    public String board(@PathVariable String category, @RequestParam Integer page,
                        @RequestParam String sort, Model model) {
        //category 아이템들을 sort 기준으로 정렬, 현재 페이지 page
        Pagination pagination = itemService.sortByCategoryAndPage(sort, category, page);
        List<String> categoryNameList = categoryService.getCategoryNameList();

        model.addAttribute("pagination", pagination);
        model.addAttribute("categoryNameList", categoryNameList);
        return "item/board/board";
    }

    @GetMapping("/image/{filename}")
    @ResponseBody
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:"+imageStorePath+filename);
    }
}
