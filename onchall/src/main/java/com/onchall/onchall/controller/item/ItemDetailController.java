package com.onchall.onchall.controller.item;

import com.onchall.onchall.dto.BoardItemDto;
import com.onchall.onchall.dto.ItemDetail;
import com.onchall.onchall.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ItemDetailController {
    private final ItemService itemService;

    @GetMapping("/itemDetail/{itemId}")
    public String itemDetail(@PathVariable Long itemId, Model model){
        ItemDetail itemDetail = itemService.getItemDetail(itemId);
        List<BoardItemDto> relatedItems = itemService.getItemByCategory(itemDetail.getCategory());
        model.addAttribute("itemDetail", itemDetail);
        model.addAttribute("relatedItems", relatedItems);
        return "item/itemDetail";
    }
}
