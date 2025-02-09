package com.ssgsak.dudo.workRecommend.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class JobRecommendResult {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rank;
    private String recommend_job_name;
    private String recommend_job_url1;
    private String recommend_job_url2;
    private String recommend_job_url3;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_recommend_list_id")
    private WorkRecommendList workRecommendList;

    public void changeWorkRecommendList(WorkRecommendList workRecommendList) {
        this.workRecommendList = workRecommendList;
    }

    @Builder
    public JobRecommendResult(int rank, String recommend_job_name, String recommend_job_url1, String recommend_job_url2, String recommend_job_url3, WorkRecommendList workRecommendList) {
        this.rank = rank;
        this.recommend_job_name = recommend_job_name;
        this.recommend_job_url1 = recommend_job_url1;
        this.recommend_job_url2 = recommend_job_url2;
        this.recommend_job_url3 = recommend_job_url3;
        changeWorkRecommendList(workRecommendList);
    }
}
