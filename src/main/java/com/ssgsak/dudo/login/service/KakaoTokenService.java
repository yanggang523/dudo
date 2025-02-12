package com.ssgsak.dudo.login.service;

import com.ssgsak.dudo.login.domain.KakaoToken;
import com.ssgsak.dudo.login.domain.KakaoUser;
import com.ssgsak.dudo.login.dto.KakaoAccessTokenDTO;
import com.ssgsak.dudo.login.repository.KakaoTokenRepository;
import com.ssgsak.dudo.login.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class KakaoTokenService {
/*
    private final KakaoTokenRepository kakaoTokenRepository;
    private final UserRepository userRepository;


    public KakaoTokenService(KakaoTokenRepository kakaoTokenRepository, UserRepository userRepository) {
        this.kakaoTokenRepository = kakaoTokenRepository;
        this.userRepository = userRepository;
    }

    // email, nickname, kakaoAccesstokenDTO 기준으로 조회
    public void saveOrUpdateKakaoToken(String email, String nickname, KakaoAccessTokenDTO kakaoAccesstokenDTO) {
        KakaoUser kakaoUser = userRepository.findByEmail(email)
                .map(existingUser -> {
                    // 기존 사용자의 닉네임이 변경되었으면 업데이트
                    if (!existingUser.getNickname().equals(nickname)) {
                        existingUser.setNickname(nickname);
                        userRepository.save(existingUser);
                    }
                    return existingUser;
                })
                .orElseGet(() -> userRepository.save(new KakaoUser(email, nickname))); // email 기준 중복 검사

            kakaoTokenRepository.findByKakaoUserEmail(email) // email 기준 조회
                    .ifPresentOrElse(existingToken -> {
                        existingToken.setKakaoAccessToken(kakaoAccesstokenDTO.getAccess_token());
                        existingToken.setKakaoRefreshToken(kakaoAccesstokenDTO.getRefresh_token());
                        existingToken.setKakaoExpiresIn(LocalDateTime.now().plus(kakaoAccesstokenDTO.getExpires_in(), ChronoUnit.SECONDS));
                        existingToken.setKakaoRefreshExpiresIn(LocalDateTime.now().plus(kakaoAccesstokenDTO.getRefresh_token_expires_in(), ChronoUnit.SECONDS));
                        kakaoTokenRepository.save(existingToken);
                    }, () -> {
                        // 새로운 토큰 저장
                        KakaoToken newToken = new KakaoToken(kakaoUser, kakaoAccesstokenDTO.getAccess_token(), kakaoAccesstokenDTO.getRefresh_token(),
                                LocalDateTime.now().plus(kakaoAccesstokenDTO.getExpires_in(), ChronoUnit.SECONDS),
                                LocalDateTime.now().plus(kakaoAccesstokenDTO.getRefresh_token_expires_in(), ChronoUnit.SECONDS));
                        kakaoTokenRepository.save(newToken);
                    });
    }

 */
}
