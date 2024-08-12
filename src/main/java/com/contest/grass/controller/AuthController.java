package com.contest.grass.controller;

import com.contest.grass.entity.GoogleUser;
import com.contest.grass.entity.KakaoUser;
import com.contest.grass.entity.User;
import com.contest.grass.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private WebClient.Builder webClientBuilder;

    @PostMapping("/google")
    public User authenticateWithGoogle(@RequestBody String token) {
        WebClient webClient = webClientBuilder.build();

        GoogleUser googleUser = webClient.get()
                .uri("https://www.googleapis.com/oauth2/v3/tokeninfo?id_token={token}", token)
                .retrieve()
                .bodyToMono(GoogleUser.class)
                .block();

        User user = userRepository.findByGoogleId(googleUser.getSub());
        if (user == null) {
            user = new User();
            user.setEmail(googleUser.getEmail());
            user.setGoogleId(googleUser.getSub());
            userRepository.save(user);
        }
        return user;
    }


    @PostMapping("/kakao")
    public User authenticateWithKakao(@RequestBody String token) {
        WebClient webClient = webClientBuilder.build();

        KakaoUser kakaoUser = webClient.get()
                .uri("https://kapi.kakao.com/v2/user/me")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(KakaoUser.class)
                .block();

        User user = userRepository.findByKakaoId(kakaoUser.getId());
        if (user == null) {
            user = new User();
            user.setEmail(kakaoUser.getKakao_account().getEmail());
            user.setKakaoId(kakaoUser.getId());
            userRepository.save(user);
        }
        return user;
    }

}
