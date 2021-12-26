package com.onchall.onchall.controller.item;

import com.onchall.onchall.SessionData;
import com.onchall.onchall.argumentResolver.Login;
import com.onchall.onchall.dto.CartDto;
import com.onchall.onchall.dto.CartItemDto;
import com.onchall.onchall.dto.MemberDetail;
import com.onchall.onchall.entity.CartItem;
import com.onchall.onchall.entity.Item;
import com.onchall.onchall.entity.Member;
import com.onchall.onchall.service.CartService;
import com.onchall.onchall.service.MemberService;
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
    private final CartService cartService;
    private final MemberService memberService;

    @GetMapping("/cart")
    public String cartList(@Login SessionData loginMemberId, Model model) {
        Member loginMember = memberService.getMemberByMemberId(loginMemberId.getMemberId());
        List<CartItem> cartItem = cartService.getCartItemListByMemberId(loginMember.getId());
        List<CartItemDto> cartItemDtoList = new ArrayList<>();
        Integer totalPrice = 0;
        for (CartItem e : cartItem) {
            Item item = e.getItem();
            Integer price = item.getPrice();
            cartItemDtoList.add(new CartItemDto(item.getName(), price, item.getId()));
            totalPrice += price;
        }
        CartDto cartDto = new CartDto(cartItemDtoList, totalPrice);
        model.addAttribute("cart", cartDto);
        model.addAttribute("member", new MemberDetail
                (loginMember.getName(), loginMember.getEmail(), loginMember.getPoint()));
        return "member/cart/cart";
    }

    @GetMapping("/cart/add/{itemId}")
    @ResponseBody
    public String addItemInCart(@Login SessionData loginMemberId, @PathVariable Long itemId) {
        try {
            cartService.addItem(loginMemberId.getMemberId(), itemId);
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
        return "true";
    }

    @GetMapping("/cart/delete/{itemId}")
    public String deleteItemInCart(@Login SessionData loginMemberId, @PathVariable("itemId") Long itemId) {
        cartService.deleteItem(loginMemberId.getMemberId(), itemId);
        return "redirect:/cart";
    }

}
