package com.ssgsak.dudo.login.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoAccessTokenDTO {
    // access 토큰 응답 파라미터와 변수명 동일
    private String token_type;
    private String access_token;
    private int expires_in; // access 토큰 만료 시간
    private String refresh_token; // 리프레쉬 토큰 값
    private int refresh_token_expires_in; // 리프레쉬 토큰 만료 시간
}