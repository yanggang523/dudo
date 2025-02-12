package com.ssgsak.dudo.resume.request;

import lombok.Builder;
import lombok.Data;

@Data
public class ResumeMainInfoSkillLanguage {

    private String resume_skills; // 보유기술
    private String resume_languages; // 외국어

    @Builder
    public ResumeMainInfoSkillLanguage(String resume_skills, String resume_languages) {
        this.resume_skills = resume_skills;
        this.resume_languages = resume_languages;
    }

}
