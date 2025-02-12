package com.ssgsak.dudo.workRecommend.request;

import lombok.Builder;
import lombok.Data;

@Data
public class FinalJobSelectRequest {

    private String recommend_company_names; // 회사명
    private String recommend_company_careers;  // 경력
    private String recommend_certifications; // 학력
    private String recommend_pride_joy; // 보람과 졸거움
    private String recommend_sigungu; // 시군구
    private String recommend_commute_hour; // 통근시간
    private String recommend_work_hour; // 근무시간
    private String recommend_work_field; // 원하는 업무분야


    @Builder
    public FinalJobSelectRequest(String recommend_company_names, String recommend_company_careers, String recommend_certifications, String recommend_pride_joy, String recommend_sigungu, String recommend_commute_hour, String recommend_work_hour, String recommend_work_field) {
        this.recommend_company_names = recommend_company_names;
        this.recommend_company_careers = recommend_company_careers;
        this.recommend_certifications = recommend_certifications;
        this.recommend_pride_joy = recommend_pride_joy;
        this.recommend_sigungu = recommend_sigungu;
        this.recommend_commute_hour = recommend_commute_hour;
        this.recommend_work_hour = recommend_work_hour;
        this.recommend_work_field = recommend_work_field;
    }



}
