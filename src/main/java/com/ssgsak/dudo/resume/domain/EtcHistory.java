package com.ssgsak.dudo.resume.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EtcHistory {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String resume_skills; // 보유기술
    private String resume_languages; // 외국어
    private String resume_certificates; // 자격증
    private String resume_education; // 교육사항
    private String resume_awards; // 수상경력
    private String resume_volunteers; // 봉사활동



}
