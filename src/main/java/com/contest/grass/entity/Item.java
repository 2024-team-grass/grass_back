package com.contest.grass.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId; // 변수명은 소문자로 시작하는 것이 관례입니다.

    @Column(length = 10)
    private String title; // 상품명

    private Integer price;

    private String sale;

    private Integer qty; // 재고수량
}
