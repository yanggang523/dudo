package com.ssgsak.dudo.workRecommend.service;

import com.ssgsak.dudo.workRecommend.domain.WorkQuestionRecommend;
import com.ssgsak.dudo.workRecommend.domain.WorkRecommendList;
import com.ssgsak.dudo.workRecommend.repository.WorkQuestionRecommendRepository;
import com.ssgsak.dudo.workRecommend.repository.WorkRecommendListRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class WorkQuestionRecommendServiceTest {
    @Autowired
    WorkRecommendListRepository workRecommendListRepository;
    @Autowired
    WorkQuestionRecommendRepository workQuestionRecommendRepository;


    @Autowired
    WorkQuestionRecommendService workQuestionRecommendService;

    @Test
    void test1() throws Exception{
        //given
        WorkRecommendList workRecommendList = new WorkRecommendList("테스트 입니다!!!");
        WorkRecommendList savedWorkReccomdList = workRecommendListRepository.save(workRecommendList);

        //when
        WorkQuestionRecommend workQuestionRecommend = new WorkQuestionRecommend("삼성", "신입", "컴활, 토익 700 이상", "아이들을 가르칠때");

        workRecommendList.setWorkQuestionRecommend(workQuestionRecommend);

        workQuestionRecommendRepository.save(workQuestionRecommend);
        //then
        String workQuestionForWorkField = workQuestionRecommendService.getWorkQuestionForWorkField(savedWorkReccomdList.getId());

        System.out.println("workQuestionForWorkField = " + workQuestionForWorkField);


    }
}