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
public class CompanyHistory {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String resume_company; // 회사명
    private int resume_service_year; // 근무년수
    private String resume_job_responsibilities; // 담당업무


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_main_info_id")
    private ResumeMainInfo resumeMainInfo;

    public void changeResumeInfo(ResumeMainInfo resumeMainInfo) {
        this.resumeMainInfo = resumeMainInfo;
    }

    @Builder
    public CompanyHistory(String resume_company, int resume_service_year, String resume_job_responsibilities) {
        this.resume_company = resume_company;
        this.resume_service_year = resume_service_year;
        this.resume_job_responsibilities = resume_job_responsibilities;

    }

}
