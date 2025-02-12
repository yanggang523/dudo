package com.ssgsak.dudo.resume.request;

import lombok.Builder;
import lombok.Data;

@Data
public class ResumeMainInfoCertificationEducation {

    private String resume_certificates; // 자격증
    private String resume_education; // 교육사항

    @Builder
    public ResumeMainInfoCertificationEducation(String resume_certificates, String resume_education) {
        this.resume_certificates = resume_certificates;
        this.resume_education = resume_education;

    }



}
