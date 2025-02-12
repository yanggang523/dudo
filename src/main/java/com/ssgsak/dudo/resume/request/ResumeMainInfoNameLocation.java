package com.ssgsak.dudo.resume.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ResumeMainInfoNameLocation {
    private String resume_name; // 이력서 이름
    private LocalDate resume_birth; // 생년월일
    private String resume_address; // 주소

    @Builder
    public ResumeMainInfoNameLocation(String resume_name, LocalDate resume_birth, String resume_address) {
        this.resume_name = resume_name;
        this.resume_birth = resume_birth;
        this.resume_address = resume_address;
    }
}
