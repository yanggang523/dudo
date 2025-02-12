package com.ssgsak.dudo.workRecommend.repository;

import com.ssgsak.dudo.workRecommend.domain.WorkQuestionRecommend;
import com.ssgsak.dudo.workRecommend.domain.WorkRecommendList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkQuestionRecommendRepository extends JpaRepository<WorkQuestionRecommend, Long> {

    Optional<Object> findByWorkRecommendList(WorkRecommendList workRecommendList);

}
