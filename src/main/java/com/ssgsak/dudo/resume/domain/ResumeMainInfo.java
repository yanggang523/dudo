package com.ssgsak.dudo.resume.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor
@ToString(of = {"id", "resume_name", "resume_birth", "resume_address", "resume_email", "resume_phone", "resume_school", "resume_major"})
public class ResumeMainInfo {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String resume_name; // 이력서 이름
    private LocalDate resume_birth; // 생년월일
    private String resume_address; // 주소

    private String resume_email; // 이메일
    private String resume_phone; // 전화번호

    private String resume_school; // 학교
    private String resume_major; // 전공

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_questions_id")
    private ResumeQuestions resumeQuestions;

    public void changeResumeQuestions(ResumeQuestions resumeQuestions) {
        this.resumeQuestions = resumeQuestions;
    }

    @OneToMany(mappedBy = "resumeMainInfo", cascade = CascadeType.ALL)
    private List<CompanyHistory> companyHistoryList = new ArrayList<>();


    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "etc_history_id")
    private EtcHistory etcHistory;


    public void addCompanyHistory(CompanyHistory companyHistory) {
        companyHistoryList.add(companyHistory);
        companyHistory.changeResumeInfo(this);
    }

    @Builder
    public ResumeMainInfo(String resume_name, LocalDate resume_birth, String resume_address, String resume_email, String resume_phone, String resume_school, String resume_major) {
        this.resume_name = resume_name;
        this.resume_birth = resume_birth;
        this.resume_address = resume_address;
        this.resume_email = resume_email;
        this.resume_phone = resume_phone;
        this.resume_school = resume_school;
        this.resume_major = resume_major;
    }

    public void changeResumePhone(String resume_phone, String resume_email) {
        this.resume_phone = resume_phone;
        this.resume_email = resume_email;
    }

    public void changeResumeSchool(String resume_school, String resume_major) {
        this.resume_school = resume_school;
        this.resume_major = resume_major;
    }

    public void changeResume_skill_language(String resume_skills, String resume_languages) {
        this.etcHistory = new EtcHistory(resume_skills, resume_languages);
        etcHistory.changeResumeMainInfo(this);
    }


}
