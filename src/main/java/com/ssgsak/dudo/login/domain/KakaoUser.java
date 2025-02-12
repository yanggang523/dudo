package com.ssgsak.dudo.login.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class KakaoUser { // 왜, 무슨 생각으로 KakaoUser라고 한 걸까... 시간 남으면 전부 User로 바꾸기...


    @Column(length = 50)
    private String nickname;

    @Id
    @Column(unique = true, length = 100)
    private String email;


    public KakaoUser(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
    }
}
