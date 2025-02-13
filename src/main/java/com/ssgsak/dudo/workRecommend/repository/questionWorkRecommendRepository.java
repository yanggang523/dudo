package com.ssgsak.dudo.workRecommend.repository;

import com.ssgsak.dudo.workRecommend.domain.QuestionWorkRecommend;
import com.ssgsak.dudo.workRecommend.domain.UserWorkRecommend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface questionWorkRecommendRepository extends JpaRepository<QuestionWorkRecommend, Long> {

    Optional<Object> findByUserWorkRecommend(UserWorkRecommend userWorkRecommend);

}
