package com.contest.grass.repository;

import com.contest.grass.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
//    void deleteByItemId(Long itemId);
}