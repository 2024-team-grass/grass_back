package com.contest.grass.repository;

import com.contest.grass.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
//    void deleteByItemId(Long itemId);
}