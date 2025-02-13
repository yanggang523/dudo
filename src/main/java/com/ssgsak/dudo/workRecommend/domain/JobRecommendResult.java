package com.ssgsak.dudo.workRecommend.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "job_recommend_result")
public class JobRecommendResult {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int result_rank;
    private String recommend_job_name;
    private String recommend_job_url1;
    private String recommend_job_url2;
    private String recommend_job_url3;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_work_recommend_id")
    private UserWorkRecommend userWorkRecommend;

    public void changeWorkRecommend(UserWorkRecommend userWorkRecommend) {
        this.userWorkRecommend = userWorkRecommend;
    }

}
