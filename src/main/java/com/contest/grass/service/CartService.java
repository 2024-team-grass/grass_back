package com.contest.grass.service;

import com.contest.grass.dto.CartResponseDto;
import com.contest.grass.entity.Cart;
import com.contest.grass.entity.item.Item;
import com.contest.grass.repository.CartRepository;
import com.contest.grass.repository.ItemRepository; // ItemRepository 임포트
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ItemRepository itemRepository; // ItemRepository 주입

    @Autowired
    public CartService(CartRepository cartRepository, ItemRepository itemRepository) {
        this.cartRepository = cartRepository;
        this.itemRepository = itemRepository;
    }

    // 1. 모든 카트 항목 조회
    public List<Cart> getAllCartItems() {
        List<Cart> carts = cartRepository.findAll();
        for (Cart cart : carts) {
            updateCartPrices(cart);
        }
        return carts;
    }

    // 2. 특정 카트 항목 조회
    public Cart getCartItem(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart item not found with id: " + cartId));
        updateCartPrices(cart); // 조회 시 가격 정보 업데이트
        return cart;
    }

    // 3. 상품 개수 업데이트
    public Cart updateCartItemCount(Long cartId, Integer count) {
        Cart cart = getCartItem(cartId);
        cart.setCount(count);
        updateCartPrices(cart); // 개수 변경 시 가격 정보 업데이트
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
        cartRepository.save(cart);
    }

    // 카트의 가격 정보를 업데이트하는 메서드
    private void updateCartPrices(Cart cart) {
        Item item = itemRepository.findById(cart.getItem().getId())
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + cart.getItem().getId()));

        String itemPriceString = item.getPrice();
        String itemPriceStringWithoutComma = itemPriceString.replace(",", "");
        double itemPrice;
        try {
            itemPrice = Double.parseDouble(itemPriceStringWithoutComma);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid item price format: " + itemPriceString);
        }

        cart.setItemPrice(itemPrice);
        cart.setTotalPrice(cart.getCount() * itemPrice + cart.getShippingFee());
    }

    // 사용자 ID로 장바구니 항목 조회
    public List<Cart> getCartItemsByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }


}
