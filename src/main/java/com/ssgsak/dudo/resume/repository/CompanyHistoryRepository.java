package com.ssgsak.dudo.resume.repository;

import com.ssgsak.dudo.resume.domain.CompanyHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyHistoryRepository extends JpaRepository<CompanyHistory, Long> {
    
}
