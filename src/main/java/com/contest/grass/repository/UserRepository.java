package com.contest.grass.repository;

import com.contest.grass.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByNickname(String nickname);
    Optional<User> findByPhoneNumber(String phoneNumber);
    Optional<User> findById(Long id);
    boolean existsByEmail(String email);


    List<User> findByName(String name);
}
