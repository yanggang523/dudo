package com.ssgsak.dudo.workRecommend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssgsak.dudo.workRecommend.domain.WorkQuestionRecommend;
import com.ssgsak.dudo.workRecommend.domain.WorkRecommendList;
import com.ssgsak.dudo.workRecommend.repository.WorkQuestionRecommendRepository;
import com.ssgsak.dudo.workRecommend.repository.WorkRecommendListRepository;
import com.ssgsak.dudo.workRecommend.request.WorkFieldRequestForAi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkQuestionRecommendService {

    private final WorkQuestionRecommendRepository workQuestionRecommendRepository;

    private final WorkRecommendListRepository workRecommendListRepository;

    private final WorkQuestionOpenAiService workQuestionOpenAiService;

    public String getWorkQuestionForWorkField(Long workRecommendListId) throws JsonProcessingException {

        WorkRecommendList workRecommendList = workRecommendListRepository.findById(workRecommendListId).orElseThrow();

        WorkQuestionRecommend questionRecommend = (WorkQuestionRecommend) workQuestionRecommendRepository.findByWorkRecommendList(workRecommendList).orElseThrow();

        WorkFieldRequestForAi workFieldRequestForAi = WorkFieldRequestForAi.builder()
                .recommend_company_names(questionRecommend.getRecommend_company_names())
                .recommend_company_careers(questionRecommend.getRecommend_company_careers())
                .recommend_certifications(questionRecommend.getRecommend_certifications())
                .recommend_pride_joy(questionRecommend.getRecommend_pride_joy())
                .build();


        return workQuestionOpenAiService.getWorkField(workFieldRequestForAi);

    }


}
