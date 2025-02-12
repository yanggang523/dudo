package com.ssgsak.dudo.login.repository;

import com.ssgsak.dudo.login.domain.UserToken;
import com.ssgsak.dudo.login.domain.KakaoUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserTokenRepository extends JpaRepository<UserToken, Long> {
    Optional<UserToken> findByKakaoUser(KakaoUser kakaoUser); // 사용자의 토큰 찾기
}
