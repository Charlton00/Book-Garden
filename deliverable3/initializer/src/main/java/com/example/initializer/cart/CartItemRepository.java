package com.example.initializer.cart;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findById(long id);

	CartItem findByCartId(long cartId);

    CartItem findByCartIdAndIsbn(long cartId, long bookIsbn);

    List<CartItem> findAllByCartId(long cartId);

}