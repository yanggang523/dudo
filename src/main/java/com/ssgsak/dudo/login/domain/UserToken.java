package com.ssgsak.dudo.login.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user_token")
public class UserToken {


    @Id
    private String email;

    @OneToOne
    @MapsId
    @JoinColumn(name = "email", nullable = false) // KakaoUser 테이블의 email과 FK 관계
    private KakaoUser kakaoUser;

    @Column(nullable = false)
    private String accessToken; // 액세스 토큰

    @Column(nullable = false)
    private String refreshToken; // 리프레시 토큰

    @Column(nullable = false)
    private LocalDateTime expiresIn; // 액세스 토큰 만료 시간

    @Setter
    @Column(nullable = false)
    private LocalDateTime refreshExpiresIn; // 리프레시 토큰 만료 시간

    public UserToken(KakaoUser kakaoUser, String accessToken, String refreshToken, LocalDateTime expiresIn, LocalDateTime refreshExpiresIn) {
        this.kakaoUser = kakaoUser;
        this.email = kakaoUser.getEmail();
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
        this.refreshExpiresIn = refreshExpiresIn;
    }


}

