package com.ssgsak.dudo.login.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


// application.properties 반영 안 되어서 생성, 추후 삭제할 예정
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "kakao.auth") // "kakao.auth" 하위 설정을 자동 주입
public class KakaoConfig {
    private String client;
    private String redirect;
}
