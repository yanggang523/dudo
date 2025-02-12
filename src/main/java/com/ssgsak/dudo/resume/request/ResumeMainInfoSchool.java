package com.ssgsak.dudo.resume.request;

import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

import java.util.List;

@Data
public class ResumeMainInfoSchool {

    private String resume_school; // 학교
    private String resume_major; // 전공

    @Builder

    public ResumeMainInfoSchool(String resume_school, String resume_major) {
        this.resume_school = resume_school;
        this.resume_major = resume_major;
    }
}
