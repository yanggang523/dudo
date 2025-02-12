package com.ssgsak.dudo.login.repository;

import com.ssgsak.dudo.login.domain.KakaoUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<KakaoUser, Long> {
    Optional<KakaoUser> findByEmail(String email);
}
