package com.onchall.onchall.controller.item;

import com.onchall.onchall.argumentResolver.Login;
import com.onchall.onchall.dto.CartDto;
import com.onchall.onchall.dto.CartItemDto;
import com.onchall.onchall.entity.CartItem;
import com.onchall.onchall.entity.Item;
import com.onchall.onchall.entity.Member;
import com.onchall.onchall.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CartController {
    public final CartService cartService;

    @GetMapping("/cart")
    public String cartList(@Login Member loginMember, Model model){
        List<CartItem> cartItem = cartService.getCartItemListByMemberId(loginMember.getId());
        List<CartItemDto> cartItemDtoList = new ArrayList<>();
        Integer totalPrice=0;
        for(CartItem e : cartItem){
            Item item = e.getItem();
            Integer price =item.getPrice();
            cartItemDtoList.add(new CartItemDto(item.getName(), price, item.getId()));
            totalPrice+=price;
        }
        CartDto cartDto = new CartDto(cartItemDtoList, totalPrice);
        model.addAttribute("cart", cartDto);
        return "member/cart/cart";
    }

    @GetMapping("/cart/add/{itemId}")
    //@ResponseBody
    public String addItemInCart(@Login Member loginMember, @PathVariable Long itemId){
        cartService.addItem(loginMember.getId(), itemId);
        //todo json 형식으로 성공여부 리턴.
        //return "true";
        //return "false";
        return "redirect:/cart";
    }

    @GetMapping("/cart/delete/{itemId}")
    public String deleteItemInCart(@Login Member loginMember, @PathVariable("itemId") Long itemId){
        cartService.deleteItem(loginMember.getId(), itemId);
        return "redirect:/cart";
    }

}
