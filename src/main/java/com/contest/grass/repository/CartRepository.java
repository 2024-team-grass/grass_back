package com.contest.grass.repository;

import com.contest.grass.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
//    void deleteByItemId(Long itemId);

    List<Cart> findByUserId(Long userId); // 사용자 ID로 장바구니 항목 조회

}