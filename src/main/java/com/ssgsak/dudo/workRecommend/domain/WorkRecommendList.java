package com.ssgsak.dudo.workRecommend.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkRecommendList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String temp_job_title; // 추천직무

    @LastModifiedDate
    private LocalDateTime recommend_publish_date; // 추천일자

    
    

    public WorkRecommendList(String temp_job_title) {
        this.temp_job_title = temp_job_title;
    }




    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "work_question_recommend_id")
    private WorkQuestionRecommend workQuestionRecommend;

    public void setWorkQuestionRecommend(WorkQuestionRecommend workQuestionRecommend) {
        this.workQuestionRecommend = workQuestionRecommend;
    }

    @PreUpdate
    public void updateRecommendPublishDate() {
        this.recommend_publish_date = LocalDateTime.now();
    }

}
