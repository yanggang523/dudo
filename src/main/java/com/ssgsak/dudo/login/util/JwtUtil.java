package com.ssgsak.dudo.login.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.ArrayList;
import java.util.Date;



@Component
@Slf4j
public class JwtUtil {

    // 키 생성 (애플리케이션 시작 시 생성)
    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // 액세스 토큰 만료 시간 (현재 7일)
    private static final long ACCESS_EXPIRATION_TIME = 1000 * 60 * 60 * 24 *7;

    // 리프레시 토큰 만료 시간 (7일)

    private static final long REFRESH_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7;

//     private final long refreshExpirationTime = 1000 * 60 * 60 * 24 * 7;
//     // JWT 토큰 유효성 검사
//     public boolean validateToken(String token) {
//         try {
//             Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);  // 서명 검증
//             return true;
//         } catch (Exception e) {
//             return false;
//         }
//     }
    // JWT 토큰에서 사용자 인증 정보를 가져옴
//     public Authentication getAuthentication(String token) {
//         Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
//         String username = claims.getSubject();

//         // 여기서 UserDetailsService를 이용해 사용자 정보를 조회하거나, 직접 사용자 객체를 생성할 수 있습니다.
//         return new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
//     }


    // 토큰에서 만료 시간 가져오기
//     public Date getExpirationDateFromToken(String token) {
//         Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
//         return claims.getExpiration();
//     }

    /**
     * 액세스 토큰 생성
     *
     * @param email 사용자 이메일
     * @return 생성된 JWT 액세스 토큰
     */
    public String createAccessToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();
    }

    /**
     * 리프레시 토큰 생성
     *
     * @param email 사용자 이메일
     * @return 생성된 JWT 리프레시 토큰
     */
    public String createRefreshToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();
    }

    /**
     * 토큰에서 이메일 추출
     *
     * @param token JWT 토큰
     * @return 이메일
     */
    public String getEmailFromToken(String token) {
        log.debug("jwtUtil에서 이런 토큰이 오고 있습니다(getEmailFromToken): {}", token);

        try {
            // 1. Bearer 제거 로직 추가
            if (token != null && token.startsWith("Bearer ")) {
                log.debug("Bearer 토큰으로 인식되었습니다.");
                token = token.substring(7).trim(); // Bearer 제거
                log.debug("bearer 제거된 Token: {}", token);
            }

            // 2. 토큰 파트 나누기
            String[] tokenParts = token.split("\\.");
            if (tokenParts.length != 3) {
                throw new IllegalArgumentException("유효하지 않은 JWT 형식입니다.(IllegalArgumentException)");
            }

            // 3. 페이로드(Base64URL 디코딩)
            String payload = new String(Base64.getUrlDecoder().decode(tokenParts[1]), StandardCharsets.UTF_8);
            log.debug("Decoded Payload: {}", payload);

            // 4. Jwts 라이브러리로 서명 검증
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey) // 서명 키 설정
                    .build()
                    .parseClaimsJws(token) // 서명 검증
                    .getBody();


            // 5. 이메일 반환
            return claims.getSubject(); // JWT의 subject에서 이메일 반환

        } catch (IllegalArgumentException e) {
            log.error("유효하지 않은 JWT 토큰 형식입니다: {}", e.getMessage());
            throw new IllegalArgumentException("유효하지 않은 JWT 토큰입니다.");
        } catch (Exception e) {
            log.error("토큰에서 이메일 추출 실패: {}", e.getMessage());
            throw new IllegalArgumentException("유효하지 않은 JWT 토큰입니다.(IllegalArgumentException)");
        }
    }

    /**
     * 디버깅용: JWT 토큰 디코딩
     *
     * @param token 디코딩할 JWT 토큰
     */
    public static void debugJwtToken(String token) {
        log.debug("jwtUtil에서 이런 토큰이 오고 있습니다(debugJwtToken): {}", token);

        try {
            // 토큰 파트 나누기
            String[] tokenParts = token.split("\\.");
            if (tokenParts.length != 3) {
                log.error("유효하지 않은 JWT 형식입니다. 파트 수: {}", tokenParts.length);
                return; // 잘못된 형식이면 메서드 종료
            }

            // Raw 토큰 파트 로깅
            log.debug("Raw Header: {}", tokenParts[0]);
            log.debug("Raw Payload: {}", tokenParts[1]);
            log.debug("Raw Signature: {}", tokenParts[2]);

            // 헤더 디코딩
            try {
                String header = new String(Base64.getUrlDecoder().decode(tokenParts[0]), StandardCharsets.UTF_8);
                log.debug("Decoded Header: {}", header);
            } catch (Exception e) {
                log.error("JWT 헤더 디코딩 실패: {}", e.getMessage());
            }

            // 페이로드 디코딩
            try {
                String payload = new String(Base64.getUrlDecoder().decode(tokenParts[1]), StandardCharsets.UTF_8);
                log.debug("Decoded Payload: {}", payload);
            } catch (Exception e) {
                log.error("JWT 페이로드 디코딩 실패: {}", e.getMessage());
            }
        } catch (Exception e) {
            log.error("JWT 디코딩 실패: {}", e.getMessage());
        }
    }

}
