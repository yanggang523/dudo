package com.ssgsak.dudo.resume.repository;

import com.ssgsak.dudo.resume.domain.ResumeMainInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeMainInfoRepository extends JpaRepository<ResumeMainInfo, Long> {
}
