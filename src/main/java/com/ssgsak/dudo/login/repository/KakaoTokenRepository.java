package com.ssgsak.dudo.login.repository;

import com.ssgsak.dudo.login.domain.KakaoToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KakaoTokenRepository extends JpaRepository<KakaoToken, Long> {
    Optional<KakaoToken> findByKakaoUserEmail(String email);

}