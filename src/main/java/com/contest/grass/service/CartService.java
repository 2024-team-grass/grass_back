package com.contest.grass.service;

import com.contest.grass.entity.Cart;
import com.contest.grass.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    // 1. 모든 카트 항목 조회
    public List<Cart> getAllCartItems() {
        return cartRepository.findAll();
    }

    // 2. 특정 카트 항목 조회
    public Cart getCartItem(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart item not found with id: " + cartId));
    }

    // 3. 상품 개수 업데이트
    public Cart updateCartItemCount(Long cartId, Integer count) {
        Cart cart = getCartItem(cartId);
        cart.setCount(count);
        cart.setTotalPrice(cart.getItemPrice() * count + cart.getShippingFee());
        return cartRepository.save(cart);
    }

    // 4. 총 금액 업데이트
    public Cart updateTotalPrice(Long cartId, double itemPrice, double shippingFee) {
        Cart cart = getCartItem(cartId);
        cart.setItemPrice(itemPrice);
        cart.setShippingFee(shippingFee);
        cart.setTotalPrice(itemPrice * cart.getCount() + shippingFee);
        return cartRepository.save(cart);
    }

    // 5. 구매 처리 로직
    @Transactional
    public boolean processPurchase(Cart cart) {
        // 5.1 결제 관련 로직을 여기에 추가 (예: 결제 API와의 연동)
        boolean paymentSuccess = simulatePayment(cart); // 가상의 결제 성공 처리

        if (paymentSuccess) {
            confirmPurchase(cart);
            return true;
        } else {
            return false;
        }
    }

    // 6. 가상 결제 프로세스 (실제 결제 시스템과의 연동이 필요)
    private boolean simulatePayment(Cart cart) {
        // 결제가 성공하는 것으로 가정한 가상 처리
        System.out.println("결제 처리 중... 장바구니 ID: " + cart.getCartId() + ", 총 금액: " + cart.getTotalPrice());
        return true;
    }

    // 7. 구매 확정 처리 (결제가 성공한 경우에 호출)
    @Transactional
    protected void confirmPurchase(Cart cart) {
        System.out.println("구매가 확정되었습니다. 장바구니 ID: " + cart.getCartId() + ", 총 금액: " + cart.getTotalPrice());
        cartRepository.save(cart); // 구매가 완료된 후 데이터베이스에 저장
    }
}