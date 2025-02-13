package com.ssgsak.dudo.login.constants;


public class SecurityConstants {

    // 허용 url 넣는 부분
    public static final String[] ALLOW_URLS = {
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/v3/api-docs/**",
            "/api/v1/posts/**",
            "/api/v1/replies/**",
            "/login",
            "/auth/login/kakao/**",
            "/api/member/signup/**",
            "/favicon.ico",
            "/error",
//            "/api/invitation/**",
//            "/api/join",
            "/test/**",
            "/api/member/info",
            "/auth/**"
    };

}

