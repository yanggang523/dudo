package com.ssgsak.dudo.resume.request;


import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

@Data
public class ResumeMainInfoPhone {

    @NumberFormat(pattern = "###-####-####")
    private String resume_phone; // 전화번호
    @Email
    private String resume_email; // 이메일

    @Builder
    public ResumeMainInfoPhone(String resume_phone, String resume_email) {
        this.resume_phone = resume_phone;
        this.resume_email = resume_email;
    }
}
