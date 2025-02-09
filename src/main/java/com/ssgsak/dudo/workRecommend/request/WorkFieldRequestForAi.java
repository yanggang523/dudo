package com.ssgsak.dudo.workRecommend.request;

import lombok.Builder;
import lombok.Data;

@Data
public class WorkFieldRequestForAi {

    private String recommend_company_names; // 회사명
    private String recommend_company_careers;  // 경력
    private String recommend_certifications; // 학력
    private String recommend_pride_joy; // 보람과 졸거움

    @Builder
    public WorkFieldRequestForAi(String recommend_company_names, String recommend_company_careers, String recommend_certifications, String recommend_pride_joy) {
        this.recommend_company_names = recommend_company_names;
        this.recommend_company_careers = recommend_company_careers;
        this.recommend_certifications = recommend_certifications;
        this.recommend_pride_joy = recommend_pride_joy;
    }
}
