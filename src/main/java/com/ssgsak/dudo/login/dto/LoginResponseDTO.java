package com.ssgsak.dudo.login.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDTO {
    private String email;
    private String nickname;
    @Getter
    public static class Properties {
        private String nickname;
    }

    @Getter
    public static class KakaoAccount {
        private String email;
    }
}

