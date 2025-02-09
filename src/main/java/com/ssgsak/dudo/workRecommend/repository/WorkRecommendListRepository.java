package com.ssgsak.dudo.workRecommend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.jobsearchv3.domain.WorkRecommendList;

@Repository
public interface WorkRecommendListRepository extends JpaRepository<WorkRecommendList, Long> {



}
