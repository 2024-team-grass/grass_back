package com.contest.grass;

import com.contest.grass.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartupRunner implements CommandLineRunner {

    private final UserService userService;

    public ApplicationStartupRunner(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        // 애플리케이션 시작 시 기존 사용자들의 비밀번호를 암호화
        userService.encryptExistingPasswords();
    }
}
