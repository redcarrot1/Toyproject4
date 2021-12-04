package com.onchall.onchall.service;

import com.onchall.onchall.entity.Cart;
import com.onchall.onchall.entity.CartItem;
import com.onchall.onchall.entity.Item;
import com.onchall.onchall.entity.Member;
import com.onchall.onchall.repository.CartItemRepository;
import com.onchall.onchall.repository.CartRepository;
import com.onchall.onchall.repository.ItemRepository;
import com.onchall.onchall.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartService {
    private final CartItemRepository cartItemRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    public void addItem(Long memberId, Long itemId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 회원이 없습니다."));
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 아이템이 없습니다."));

        CartItem cartItem = new CartItem();
        cartItem.setCart(member.getCart());
        cartItem.setItem(item);
        cartItemRepository.save(cartItem);
    }

    public List<CartItem> getCartItemListByMemberId(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 회원이 없습니다."));
        Long cartId = member.getCart().getId();
        return cartItemRepository.findByCartId(cartId);
    }

    public void deleteItem(Long memberId, Long itemId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 회원이 없습니다."));
        Cart cart = member.getCart();
        CartItem cartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), itemId).get();
        cart.getCartItems().remove(cartItem);
        cartItemRepository.delete(cartItem);
    }

}
