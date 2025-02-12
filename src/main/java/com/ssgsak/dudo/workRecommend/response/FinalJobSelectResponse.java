package com.ssgsak.dudo.workRecommend.response;

import lombok.Builder;
import lombok.Data;

@Data
public class FinalJobSelectResponse {

    private int rank;
    private String recommend_job_name;
    private String recommend_job_url1;
    private String recommend_job_url2;
    private String recommend_job_url3;

    @Builder
    public FinalJobSelectResponse(int rank, String recommend_job_name, String recommend_job_url1, String recommend_job_url2, String recommend_job_url3) {
        this.rank = rank;
        this.recommend_job_name = recommend_job_name;
        this.recommend_job_url1 = recommend_job_url1;
        this.recommend_job_url2 = recommend_job_url2;
        this.recommend_job_url3 = recommend_job_url3;
    }

}
