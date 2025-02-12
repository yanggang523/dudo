package com.ssgsak.dudo.resume.request;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;

@Data
@Getter
public class ResumeMainInfoCreate {

    private String resume_name; // 이력서 이름
    private LocalDate resume_birth; // 생년월일
    private String resume_address; // 주소

    private String resume_email; // 이메일
    private String resume_phone; // 전화번호

    private String resume_school; // 학교
    private String resume_major; // 전공

    @Builder
    public ResumeMainInfoCreate(String resume_name, LocalDate resume_birth, String resume_address, String resume_email, String resume_phone, String resume_school, String resume_major) {
        this.resume_name = resume_name;
        this.resume_birth = resume_birth;
        this.resume_address = resume_address;
        this.resume_email = resume_email;
        this.resume_phone = resume_phone;
        this.resume_school = resume_school;
        this.resume_major = resume_major;
    }


}
