package com.ssgsak.dudo.resume.service;

import com.ssgsak.dudo.resume.domain.ResumeQuestions;
import com.ssgsak.dudo.resume.repository.ResumeQuestionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ResumeQuestionsService {

    private final ResumeQuestionsRepository resumeQuestionsRepository;


    public Long save(ResumeQuestions resumeQuestions) {
        ResumeQuestions savedReumeQuestion = resumeQuestionsRepository.save(resumeQuestions);
        return savedReumeQuestion.getId();
    }

//    @Transactional(readOnly = true)
    public ResumeQuestions findOne(Long id) {
        return resumeQuestionsRepository.findById(id).orElse(null);
    }


    public void delete(Long id) {
        resumeQuestionsRepository.deleteById(id);
    }


}
