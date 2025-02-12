package com.ssgsak.dudo.workRecommend.service;

import com.ssgsak.dudo.workRecommend.domain.QuestionWorkRecommend;
import com.ssgsak.dudo.workRecommend.domain.UserWorkRecommend;
import com.ssgsak.dudo.workRecommend.repository.UserWorkRecommendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserWorkRecommendService {

    private final UserWorkRecommendRepository userWorkRecommendRepository;
    private final com.ssgsak.dudo.workRecommend.repository.questionWorkRecommendRepository questionWorkRecommendRepository;

    public Long createWorRecommendAndWorkQuestion() {
        UserWorkRecommend test = new UserWorkRecommend();
        UserWorkRecommend savedUserWorkRecommend = userWorkRecommendRepository.save(test);

        QuestionWorkRecommend questionWorkRecommend = new QuestionWorkRecommend(savedUserWorkRecommend);
        questionWorkRecommendRepository.save(questionWorkRecommend);
        return savedUserWorkRecommend.getId();
    }

}
