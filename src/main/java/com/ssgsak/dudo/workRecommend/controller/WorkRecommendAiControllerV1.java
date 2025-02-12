package com.ssgsak.dudo.workRecommend.controller;

import com.ssgsak.dudo.workRecommend.service.QuestionWorkRecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WorkRecommendAiControllerV1 {

    private final QuestionWorkRecommendService jobQuestionRecommendService;

    @PostMapping("/api/workRecommand/preCompanynames")
    public void savePreCompanyNames(@RequestBody Long id, String recommendCompanyNames) {


//        jobQuestionRecommendService.saveRecommendCompanyNames(workRecommendList.getId(), recommendCompanyNames);

    }





}
