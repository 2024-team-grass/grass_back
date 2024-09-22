package com.contest.grass.repository;

import com.contest.grass.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
//    void deleteByItemId(Long itemId);

    List<Orders> findByUserId(Long userId);
}