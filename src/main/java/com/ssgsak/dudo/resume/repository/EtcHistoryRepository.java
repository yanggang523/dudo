package com.ssgsak.dudo.resume.repository;

import com.ssgsak.dudo.resume.domain.EtcHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EtcHistoryRepository extends JpaRepository<EtcHistory, Long> {

}
