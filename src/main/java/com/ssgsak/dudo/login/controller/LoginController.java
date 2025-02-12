package com.ssgsak.dudo.login.controller;

import com.ssgsak.dudo.login.dto.KakaoInfoDTO;
import com.ssgsak.dudo.login.service.KakaoService;
import com.ssgsak.dudo.login.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.codec.Utf8;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ssgsak.dudo.login.dto.LoginResponseDTO;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.Duration;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class LoginController {

    private final KakaoService kakaoService;
    private final JwtUtil jwtUtil;

    // 로그인 및 회원가입
    @GetMapping("/login/callback")
    public ResponseEntity<?> kakaoLogin(HttpServletRequest request) throws UnsupportedEncodingException {
        String code = request.getParameter("code"); // 인가 코드 추출
        log.info("카카오 로그인 인가 코드: {}", code);
        String kakaoAccessToken = kakaoService.getKakaoAccessToken(code); // 카카오 access 토큰 받아오기
        log.info("카카오 로그인 엑세스 토큰: {}", kakaoAccessToken);
        LoginResponseDTO loginResponse = kakaoService.getKakaoUserInfo(kakaoAccessToken); // 사용자 정보 가져오기
        String email = loginResponse.getEmail();
        String jwtAccessToken = jwtUtil.createAccessToken(email);
        String nickname = loginResponse.getNickname();
        String encodingNickname = URLEncoder.encode(nickname, "UTF-8");
        return ResponseEntity.status(HttpStatus.FOUND) // 토큰은 헤더에, 사용자 정보는 pharam으로 보내rl
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtAccessToken) // JWT 포함
                .header(HttpHeaders.LOCATION,
                        // 사용자 정보 URL에 포함
                        "http://localhost/oauth-success?nickname=" + encodingNickname)
                .build();
    }
}
