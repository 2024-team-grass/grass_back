package com.contest.grass.repository;

import com.contest.grass.entity.Week;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface WeekRepository extends JpaRepository<Week, Long> {

    @Query("SELECT w.dayOfWeek, SUM(w.postCount) " +
            "FROM Week w " +
            "WHERE w.user.id = :userId AND w.weekStartDate = :weekStartDate " +
            "GROUP BY w.dayOfWeek")
    List<Object[]> findPostCountByDayOfWeekAndUser(@Param("userId") Long userId, @Param("weekStartDate") LocalDate weekStartDate);
}
