package com.ssgsak.dudo.login.controller;

import com.ssgsak.dudo.login.service.KakaoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.ssgsak.dudo.login.dto.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.ssgsak.dudo.login.dto.LoginResponseDTO;

import java.time.Duration;

@Controller
@RequestMapping("/api/auth")
public class LoginController {

    private final KakaoService kakaoService;

    public LoginController(KakaoService kakaoService) {
        this.kakaoService = kakaoService;
    }

    // 로그인 및 회원가입
    @GetMapping("/login/oauth2/callback/kakao")
    public ResponseEntity<Void> kakaoLogin(HttpServletRequest request) {
        String code = request.getParameter("code"); // 인가 코드 추출
        String kakaoAccessToken = kakaoService.getKakaoAccessToken(code); // 카카오 access 토큰 받아오기
        LoginResponseDTO loginResponse = kakaoService.getKakaoUserInfo(kakaoAccessToken); // 사용자 정보 가져오기
        return ResponseEntity.status(HttpStatus.FOUND) // 토큰은 헤더에, 사용자 정보는 pharam으로 보내기
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + loginResponse.getJwtToken()) // JWT 포함
                .header(HttpHeaders.LOCATION,
                        // 사용자 정보 URL에 포함
                        "http://localhost/oauth-success?email=" + loginResponse.getEmail())
                .build();
    }
}
