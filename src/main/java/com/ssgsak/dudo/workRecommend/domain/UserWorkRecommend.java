package com.ssgsak.dudo.workRecommend.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class UserWorkRecommend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    private String temp_job_title; // 테스트용 나중에 삭제

    // 테스트용 생성자 나중에 삭제
//    public WorkRecommendList(String temp_job_title) {
//        this.temp_job_title = temp_job_title;
//    }

    @LastModifiedDate
    private LocalDateTime recommend_publish_date; // 추천일자

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "question_work_recommend_id")
    private QuestionWorkRecommend questionWorkRecommend;

    @OneToMany(mappedBy = "userWorkRecommend")
    private List<JobRecommendResult> jobRecommendResults = new ArrayList<>();


    public UserWorkRecommend(QuestionWorkRecommend questionWorkRecommend) {
        this.questionWorkRecommend = questionWorkRecommend;
    }

    public void setQuestionWorkRecommend(QuestionWorkRecommend questionWorkRecommend) {
        this.questionWorkRecommend = questionWorkRecommend;
    }


    public void addJobRecommendResult(JobRecommendResult jobRecommendResult) {
        jobRecommendResults.add(jobRecommendResult);
        jobRecommendResult.changeWorkRecommend(this);
    }


//    @PreUpdate
//    public void updateRecommendPublishDate() {
//        this.recommend_publish_date = LocalDateTime.now();
//    }

}
