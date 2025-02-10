package com.ssgsak.dudo.login.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssgsak.dudo.login.dto.KakaoAccessTokenDTO;
import com.ssgsak.dudo.login.dto.KakaoInfoDTO;
import com.ssgsak.dudo.login.dto.LoginResponseDTO;
import com.ssgsak.dudo.login.repository.KakaoTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final KakaoTokenRepository kakaoTokenRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String KAKAO_TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    private static final String KAKAO_USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";

    @Value("${kakao.auth.client}") // application.properties에서 가져오기
    private String clientId;

    @Value("${kakao.auth.redirect}")
    private String redirectUri;

    /**
     * 🔥 카카오 AccessToken 발급
     */
    public String getKakaoAccessToken(String code) {
        try {
            // 요청 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            // 요청 바디 설정
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("grant_type", "authorization_code");
            params.add("client_id", clientId);
            params.add("redirect_uri", redirectUri);
            params.add("code", code);

            // 요청 엔터티 생성
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

            // 카카오 API 호출
            ResponseEntity<KakaoAccessTokenDTO> response = restTemplate.exchange(
                    KAKAO_TOKEN_URL, HttpMethod.POST, requestEntity, KakaoAccessTokenDTO.class
            );

            // 응답 확인 후, 토큰 반환
            return Optional.ofNullable(response.getBody())
                    .map(KakaoAccessTokenDTO::getAccess_token)
                    .orElseThrow(() -> new RuntimeException("카카오 토큰 응답이 올바르지 않습니다."));
        } catch (Exception e) {
            log.error("카카오 토큰 요청 실패: {}", e.getMessage(), e);
            throw new RuntimeException("카카오 토큰 요청 중 오류 발생", e);
        }
    }

    public LoginResponseDTO getKakaoUserInfo(String accessToken) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();

            headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
            headers.add("Authorization", "Bearer " + accessToken);

            HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    "https://kapi.kakao.com/v2/user/me",
                    HttpMethod.GET,
                    kakaoProfileRequest,
                    String.class
            );

            ObjectMapper objectMapper = new ObjectMapper();

            // ✅ JSON 파싱 예외 처리 추가
            KakaoInfoDTO.KakaoProfile kakaoProfile;
            try {
                kakaoProfile = objectMapper.readValue(response.getBody(), KakaoInfoDTO.KakaoProfile.class);
            } catch (JsonProcessingException e) {
                log.error("JSON 파싱 오류: {}", e.getMessage(), e);
                throw new RuntimeException("카카오 사용자 정보 변환 중 오류 발생", e);
            }

            // ✅ `KakaoProfile` → `LoginResponseDTO` 변환
            return new LoginResponseDTO(
                    kakaoProfile.getKakao_account().getEmail(), // 이메일 가져오기
                    kakaoProfile.getProperties().getNickname()  // 닉네임 가져오기
            );

        } catch (Exception e) {
            log.error("카카오 사용자 정보 요청 실패: {}", e.getMessage(), e);
            throw new RuntimeException("카카오 사용자 정보 요청 중 오류 발생", e);
        }
    }
}

