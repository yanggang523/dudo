package com.ssgsak.dudo.workRecommend.repository;

import com.ssgsak.dudo.workRecommend.domain.UserWorkRecommend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserWorkRecommendRepository extends JpaRepository<UserWorkRecommend, Long> {



}
