package com.ssgsak.dudo.login.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssgsak.dudo.login.domain.KakaoUser;
import com.ssgsak.dudo.login.dto.KakaoAccessTokenDTO;
import com.ssgsak.dudo.login.dto.KakaoInfoDTO;
import com.ssgsak.dudo.login.dto.LoginResponseDTO;
import com.ssgsak.dudo.login.repository.KakaoTokenRepository;
import com.ssgsak.dudo.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoService {

    private final RestTemplate restTemplate; // DI를 통한 RestTemplate 사용
    private final KakaoTokenRepository kakaoTokenRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;


    @Value("${kakao.auth.client}")
    private String clientId;

    @Value("${kakao.auth.redirect}")
    private String redirectUri;

    /**
     * 🔥 카카오 AccessToken 발급
     */
    public String getKakaoAccessToken(String code) {
        log.info("getKakaoAccessToken"+code);
        try {

            RestTemplate restTemplate = new RestTemplate();

            // 요청 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            // 요청 바디 설정
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("grant_type", "authorization_code");
            params.add("client_id", clientId);
            params.add("redirect_uri", redirectUri);
            params.add("code", code);

            log.info("카카오 로그인 인가 코드(getKakaoAccessToken 안): {}", code);
            log.info("Kakao Token Request Params: {}", params);
            log.info("Headers: {}", headers);


            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

            // 요청 실행
            ResponseEntity<String> response = restTemplate.exchange(
                    "https://kauth.kakao.com/oauth/token",
                    HttpMethod.POST,
                    request,
                    String.class
            );

            // JSON을 DTO로 변환
            KakaoAccessTokenDTO tokenDTO = objectMapper.readValue(response.getBody(), KakaoAccessTokenDTO.class);
            return tokenDTO.getAccess_token();
        } catch (JsonProcessingException e) {
            log.error("JSON 파싱 오류: {}", e.getMessage(), e);
            throw new RuntimeException("카카오 토큰 응답 JSON 변환 오류", e);
        } catch (Exception e) {
            log.error("카카오 토큰 요청 실패: {}", e.getMessage(), e);
            throw new RuntimeException("카카오 토큰 요청 중 오류 발생", e);
        }
    }

    /**
     * 🔥 카카오 사용자 정보 가져오기
     */
    public LoginResponseDTO getKakaoUserInfo(String accessToken) {
        try {
            RestTemplate restTemplate = new RestTemplate();


            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON)); // Accept 추가
            headers.setBearerAuth(accessToken);

            HttpEntity<Void> request = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    "https://kapi.kakao.com/v2/user/me",
                    HttpMethod.GET,
                    request,
                    String.class
            );

            // JSON을 DTO로 변환
            KakaoInfoDTO.KakaoProfile kakaoProfile = objectMapper.readValue(response.getBody(), KakaoInfoDTO.KakaoProfile.class);

            // 이메일과 닉네임 추출
            String email = kakaoProfile.getKakao_account().getEmail();
            String nickname = kakaoProfile.getProperties().getNickname();

            // DB에 저장 (이미 존재하면 업데이트, 없으면 생성)
            Optional<KakaoUser> optionalUser = userRepository.findByEmail(email);
            KakaoUser kakaoUser;
            if (optionalUser.isPresent()) {
                kakaoUser = optionalUser.get();
                // 닉네임이 변경되었을 경우 업데이트 (원하는 경우)
                kakaoUser.setNickname(nickname);
            } else {
                kakaoUser = new KakaoUser();
                kakaoUser.setEmail(email);
                kakaoUser.setNickname(nickname);
            }
            userRepository.save(kakaoUser);

            return new LoginResponseDTO(email, nickname);



        } catch (JsonProcessingException e) {
            log.error("JSON 파싱 오류: {}", e.getMessage(), e);
            throw new RuntimeException("카카오 사용자 정보 JSON 변환 오류", e);
        } catch (Exception e) {
            log.error("카카오 사용자 정보 요청 실패: {}", e.getMessage(), e);
            throw new RuntimeException("카카오 사용자 정보 요청 중 오류 발생", e);
        }
    }

//    /**
//     * 🔥 공통 요청 메서드 (RestTemplate 사용)
//     */
//    private ResponseEntity<String> sendRequest(String url, HttpMethod method, HttpEntity<?> request) {
//        return restTemplate.exchange(url, method, request, String.class);
//    }
}
