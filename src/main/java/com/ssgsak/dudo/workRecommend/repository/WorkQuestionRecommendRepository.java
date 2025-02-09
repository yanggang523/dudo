package com.ssgsak.dudo.workRecommend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.jobsearchv3.domain.WorkQuestionRecommend;
import study.jobsearchv3.domain.WorkRecommendList;

import java.util.Optional;

@Repository
public interface WorkQuestionRecommendRepository extends JpaRepository<WorkQuestionRecommend, Long> {

    Optional<Object> findByWorkRecommendList(WorkRecommendList workRecommendList);

}
