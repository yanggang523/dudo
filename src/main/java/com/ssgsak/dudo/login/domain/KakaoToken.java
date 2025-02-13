package com.ssgsak.dudo.login.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "kakao_token")
public class KakaoToken {

    @Id
    private String email;

    @OneToOne
    @MapsId
    @JoinColumn(name = "email", nullable = false) // KakaoUser 테이블의 email과 FK 관계
    private KakaoUser kakaoUser;

    @Column(nullable = false)
    private String kakaoAccessToken; // 액세스 토큰

    @Column(nullable = false)
    private String kakaoRefreshToken; // 리프레시 토큰

    @Column(nullable = false)
    private LocalDateTime kakaoExpiresIn; // 액세스 토큰 만료 시간

    @Column(nullable = false)
    private LocalDateTime kakaoRefreshExpiresIn; // 리프레시 토큰 만료 시간

    public KakaoToken(KakaoUser kakaoUser, String kakaoAccessToken, String kakaoRefreshToken,
                      LocalDateTime kakaoExpiresIn, LocalDateTime kakaoRefreshExpiresIn) {
        this.kakaoUser = kakaoUser;
        this.email = kakaoUser.getEmail();
        this.kakaoAccessToken = kakaoAccessToken;
        this.kakaoRefreshToken = kakaoRefreshToken;
        this.kakaoExpiresIn = kakaoExpiresIn;
        this.kakaoRefreshExpiresIn = kakaoRefreshExpiresIn;
    }
}

