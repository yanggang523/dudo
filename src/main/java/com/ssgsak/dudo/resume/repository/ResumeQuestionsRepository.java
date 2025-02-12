package com.ssgsak.dudo.resume.repository;

import com.ssgsak.dudo.resume.domain.ResumeQuestions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ResumeQuestionsRepository extends JpaRepository<ResumeQuestions, Long> {



}
