package com.ssgsak.dudo.workRecommend.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkQuestionRecommend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String recommend_company_names; // 회사명
    private String recommend_company_careers;  // 경력
    private String recommend_certifications; // 학력
    private String recommend_pride_joy; // 보람과 졸거움

    private String recommend_sigungu; // 시군구
    private String recommend_commute_hour; // 통근시간
    private String recommend_work_hour; // 근무시간

    private String recommend_work_field; // 원하는 업무분야

    @OneToOne(mappedBy = "workQuestionRecommend")
    private WorkRecommendList workRecommendList;


    public WorkQuestionRecommend(String recommend_company_names) {
        this.recommend_company_names = recommend_company_names;
    }

    public WorkQuestionRecommend(String recommend_company_names, String recommend_company_careers, String recommend_certifications, String recommend_pride_joy) {
        this.recommend_company_names = recommend_company_names;
        this.recommend_company_careers = recommend_company_careers;
        this.recommend_certifications = recommend_certifications;
        this.recommend_pride_joy = recommend_pride_joy;
    }



    @Builder
    public WorkQuestionRecommend(String recommend_company_names, String recommend_company_careers, String recommend_certifications, String recommend_pride_joy, String recommend_employ_type, String recommend_sigungu, String recommend_commute_hour, String recommend_work_hour, String recommend_work_field) {
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
