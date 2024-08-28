package com.contest.grass.service;

import com.contest.grass.entity.Item;
import com.contest.grass.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    // 1. 모든 상품 조회
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    // 2. 특정 상품 상세 조회
    public Item getItemById(Long id) {
        Optional<Item> item = itemRepository.findById(id);
        return item.orElseThrow(() -> new RuntimeException("Item not found with id: " + id));
    }

    // 3. 특정 상품명 수정
    public Item updateItemTitle(Long id, String title) {
        Item item = getItemById(id);
        item.setTitle(title);
        return itemRepository.save(item);
    }

    // 4. 특정 상품의 재고 수량 수정
    public Item updateItemQuantity(Long id, Integer quantity) {
        Item item = getItemById(id);
        item.setQty(quantity);
        return itemRepository.save(item);
    }
}