package com.contest.grass.controller;

import com.contest.grass.entity.Item;
import com.contest.grass.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@Tag(name = "Item", description = "상품 관리 API") // Swagger 태그 추가
public class ItemController {
    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @Operation(summary = "모든 상품 조회", description = "모든 상품의 제품명, 금액, 세일가, 이미지, 카테고리를 조회")
    @GetMapping
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    @Operation(summary = "특정 상품의 상세페이지 조회", description = "특정 ID를 가진 상품의 상세 정보를 조회")
    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemDetail(@PathVariable Long id) {
        Item item = itemService.getItemById(id);
        return ResponseEntity.ok(item);
    }

    @Operation(summary = "특정 상품의 상품명 수정", description = "특정 ID를 가진 상품의 상품명을 수정")
    @PostMapping("/{id}/title")
    public ResponseEntity<Item> updateItemTitle(@PathVariable Long id, @RequestParam String title) {
        Item updatedItem = itemService.updateItemTitle(id, title);
        return ResponseEntity.ok(updatedItem);
    }

    @Operation(summary = "특정 상품의 재고 수량 수정", description = "특정 ID를 가진 상품의 재고 수량을 수정")
    @PostMapping("/{id}/quantity")
    public ResponseEntity<Item> updateItemQuantity(@PathVariable Long id, @RequestParam Integer quantity) {
        Item updatedItem = itemService.updateItemQuantity(id, quantity);
        return ResponseEntity.ok(updatedItem);
    }
}
