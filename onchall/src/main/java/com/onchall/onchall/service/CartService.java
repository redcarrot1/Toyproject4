package com.onchall.onchall.service;

import com.onchall.onchall.entity.Cart;
import com.onchall.onchall.entity.CartItem;
import com.onchall.onchall.entity.Item;
import com.onchall.onchall.repository.CartItemRepository;
import com.onchall.onchall.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartService {
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final MemberService memberService;
    private final ItemService itemService;

    public void addItem(Long memberId, Long itemId) {
        Cart memberCart = memberService.getMemberByMemberId(memberId).getCart();
        Item item = itemService.getItemByItemId(itemId);
        if (isPresent(memberCart, item)) {
            throw new IllegalArgumentException("이미 장바구니에 있는 상품입니다.");
        }

        CartItem cartItem = new CartItem();
        cartItem.setCart(memberCart);
        cartItem.setItem(item);
        cartItemRepository.save(cartItem);
    }

    public List<CartItem> getCartItemListByMemberId(Long memberId) {
        Long cartId = memberService.getMemberByMemberId(memberId).getCartId();
        return cartItemRepository.findByCartId(cartId);
    }

    public void deleteItem(Long memberId, Long itemId) {
        Cart memberCart = memberService.getMemberByMemberId(memberId).getCart();
        CartItem cartItem = cartItemRepository.findByCartIdAndItemId(memberCart.getId(), itemId)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 cartItem 이 없습니다."));
        memberCart.getCartItems().remove(cartItem);
        cartItemRepository.delete(cartItem);
    }

    private boolean isPresent(Cart cart, Item item) {
        return cartItemRepository.findByCartAndItem(cart, item).isPresent();
    }

    public void deleteCartItem(CartItem cartItem){
        cartItemRepository.delete(cartItem);
    }

}
