package com.onchall.onchall.repository;

import com.onchall.onchall.entity.Cart;
import com.onchall.onchall.entity.CartItem;
import com.onchall.onchall.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCartId(Long cartId);

    Optional<CartItem> findByCartIdAndItemId(Long CartId, Long itemId);

    Optional<CartItem> findByCartAndItem(Cart cart, Item item);
}
