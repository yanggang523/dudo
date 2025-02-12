package com.ssgsak.dudo.workRecommend.repository;

import com.ssgsak.dudo.workRecommend.domain.WorkRecommendList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkRecommendListRepository extends JpaRepository<WorkRecommendList, Long> {



}
