package com.contest.grass.controller;

import com.contest.grass.entity.Item;
import com.contest.grass.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    // 1. 제품명, 금액, 세일가, 이미지, 카테고리 조회 (GET)
    @GetMapping
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    // 2. 특정 상품의 상세페이지 조회 (GET)
    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemDetail(@PathVariable Long id) {
        Item item = itemService.getItemById(id);
        return ResponseEntity.ok(item);
    }

    // 3. 특정 상품의 상품명 수정 (POST)
    @PostMapping("/{id}/title")
    public ResponseEntity<Item> updateItemTitle(@PathVariable Long id, @RequestParam String title) {
        Item updatedItem = itemService.updateItemTitle(id, title);
        return ResponseEntity.ok(updatedItem);
    }

    // 4. 특정 상품의 재고 수량 수정 (POST)
    @PostMapping("/{id}/quantity")
    public ResponseEntity<Item> updateItemQuantity(@PathVariable Long id, @RequestParam Integer quantity) {
        Item updatedItem = itemService.updateItemQuantity(id, quantity);
        return ResponseEntity.ok(updatedItem);
    }
}