package com.contest.grass.service;

import com.contest.grass.entity.Cart;
import com.contest.grass.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    // 모든 카트 항목 조회
    public List<Cart> getAllCartItems() {
        return cartRepository.findAll();
    }

    // 특정 카트 항목 조회
    public Cart getCartItem(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart item not found with id: " + cartId));
    }

    // 상품 개수 업데이트
    public Cart updateCartItemCount(Long cartId, Integer count) {
        Cart cart = getCartItem(cartId);
        cart.setCount(count);
        cart.setTotalPrice(cart.getItemPrice() * count + cart.getShippingFee());
        return cartRepository.save(cart);
    }

    // 총 금액 업데이트
    public Cart updateTotalPrice(Long cartId, double itemPrice, double shippingFee) {
        Cart cart = getCartItem(cartId);
        cart.setItemPrice(itemPrice);
        cart.setShippingFee(shippingFee);
        cart.setTotalPrice(itemPrice * cart.getCount() + shippingFee);
        return cartRepository.save(cart);
    }
}