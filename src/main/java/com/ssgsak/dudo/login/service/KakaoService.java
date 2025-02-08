package com.ssgsak.dudo.login.service;

import com.ssgsak.dudo.login.dto.KakaoAccessTokenDTO;
import com.ssgsak.dudo.login.dto.LoginResponseDTO;
import lombok.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Service
public class KakaoService {

    private final RestTemplate restTemplate = new RestTemplate();

    private final String KAKAO_TOKEN_URL = "https://kauth.kakao.com/oauth/token";

    @Value("${kakao.auth.client}") // application.properties에서 못 가져오는 오류 있음
    private final String CLIENT_ID;// 카카오 REST API 키

    @Value("${kakao.auth.redirect}")
    private final String REDIRECT_URI; // 카카오 리다렉 url




    // kakao access token 발급
    public String getKakaoAccessToken(String code) {
        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // 요청 바디 설정 (카카오에 전송할 데이터)
        Map<String, String> params = new HashMap<>();
        params.put("grant_type", "authorization_code");
        params.put("client_id", CLIENT_ID);
        params.put("redirect_uri", REDIRECT_URI);
        params.put("code", code);

        // 요청 엔터티 생성 (헤더 + 바디)
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(params, headers);

        // 카카오 api에 POST 요청 (RestTemplate 방식, 향후 수정 필요)
        ResponseEntity<KakaoAccessTokenDTO> response = restTemplate.exchange(
                KAKAO_TOKEN_URL,
                HttpMethod.POST,
                requestEntity,
                KakaoAccessTokenDTO.class // 응답을 KakaoTokenDTO로 자동 변환
        );

        // 나중에 예외처리 해야 함
        return response.getBody().getAccess_token(); // accesstoken 반환
    }

    // 사용자 정보 가져오기
    public LoginResponseDTO getKakaoUserInfo(String code){
        // 1. 사용자 정보 요청
        // 2. 사용자 정보 확인
        // 3. 중복 유저 확인 및 저장 -> KakaoTokenService 활용

        return null;

    }



}

