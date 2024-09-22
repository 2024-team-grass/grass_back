package com.contest.grass.service;

import com.contest.grass.repository.WeekRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WeekService {

    private final WeekRepository weekRepository;

    @Autowired
    public WeekService(WeekRepository weekRepository) {
        this.weekRepository = weekRepository;
    }

    public Map<String, Long> getPostCountByDayOfWeek(Long userId, LocalDate weekStartDate) {
        List<Object[]> results = weekRepository.findPostCountByDayOfWeekAndUser(userId, weekStartDate);
        Map<String, Long> result = new HashMap<>();


        result.put("일", 0L);
        result.put("월", 0L);
        result.put("화", 0L);
        result.put("수", 0L);
        result.put("목", 0L);
        result.put("금", 0L);
        result.put("토", 0L);


        for (Object[] row : results) {
            Integer dayOfWeek = ((Number) row[0]).intValue();
            Long count = ((Number) row[1]).longValue();


            switch (dayOfWeek) {
                case 1: result.put("월", count); break;
                case 2: result.put("화", count); break;
                case 3: result.put("수", count); break;
                case 4: result.put("목", count); break;
                case 5: result.put("금", count); break;
                case 6: result.put("토", count); break;
                case 7: result.put("일", count); break;
            }
        }

        return result;
    }
}
