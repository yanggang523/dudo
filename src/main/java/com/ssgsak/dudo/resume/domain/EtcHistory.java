package com.ssgsak.dudo.resume.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
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

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "etcHistory")
    private ResumeMainInfo resumeMainInfo;

    public void changeResumeMainInfo(ResumeMainInfo resumeMainInfo) {
        this.resumeMainInfo = resumeMainInfo;
    }

    public EtcHistory(String resume_skills, String resume_languages) {
        this.resume_skills = resume_skills;
        this.resume_languages = resume_languages;
    }

    public void changeResume_certification_education(String resume_certificates, String resume_education) {
        this.resume_certificates = resume_certificates;
        this.resume_education = resume_education;
    }

    public void changeResume_award(String award) {
        this.resume_awards = award;
    }
}
