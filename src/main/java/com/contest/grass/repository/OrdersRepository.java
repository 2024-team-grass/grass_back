package com.contest.grass.repository;

import com.contest.grass.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
    // 추가적인 쿼리 메서드가 필요한 경우 여기에 정의할 수 있습니다.
}