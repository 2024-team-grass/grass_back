package com.contest.grass.controller;

import com.contest.grass.service.WeekService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/weeks")
public class WeekController {

    private final WeekService weekService;

    @Autowired
    public WeekController(WeekService weekService) {
        this.weekService = weekService;
    }

    @GetMapping("/post-count")
    public ResponseEntity<Map<String, Long>> getPostCountByDayOfWeek(
            @RequestParam Long userId,
            @RequestParam LocalDate weekStartDate) {
        Map<String, Long> postCount = weekService.getPostCountByDayOfWeek(userId, weekStartDate);
        return ResponseEntity.ok(postCount);
    }
}
