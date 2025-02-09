package com.ssgsak.dudo.workRecommend.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.jobsearchv3.domain.JobRecommendResult;

@Repository
public interface JobRecommendResultRepsotory extends JpaRepository<JobRecommendResult, Long> {





}
