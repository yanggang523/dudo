package com.ssgsak.dudo.workRecommend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import study.jobsearchv3.service.WorkQuestionRecommendService;

@RestController
@RequiredArgsConstructor
public class JobRecommendAiControllerV1 {

    private final WorkQuestionRecommendService jobQuestionRecommendService;



}
