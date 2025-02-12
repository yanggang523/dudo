package com.ssgsak.dudo.workRecommend.service;

import com.ssgsak.dudo.workRecommend.domain.WorkQuestionRecommend;
import com.ssgsak.dudo.workRecommend.domain.WorkRecommendList;
import com.ssgsak.dudo.workRecommend.repository.WorkQuestionRecommendRepository;
import com.ssgsak.dudo.workRecommend.repository.WorkRecommendListRepository;
import com.ssgsak.dudo.workRecommend.response.FinalJobSelectResponse;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class WorkQuestionRecommendServiceTest {

    @Autowired
    WorkRecommendListRepository workRecommendListRepository;
    @Autowired
    WorkQuestionRecommendRepository workQuestionRecommendRepository;


    @Autowired
    WorkQuestionRecommendService workQuestionRecommendService;


    @Autowired
    EntityManager em;


    // 테스트용 데이터
    WorkRecommendList savedWorkRecommendList;


    @Test
    void clear() {
        workQuestionRecommendRepository.deleteAll();
        workRecommendListRepository.deleteAll();
    }

    @BeforeEach
    void 유저_생성() {
        WorkRecommendList workRecommendList = new WorkRecommendList("테스트 입니다!!!");
        savedWorkRecommendList = workRecommendListRepository.save(workRecommendList);

        WorkQuestionRecommend workQuestionRecommend = new WorkQuestionRecommend("", "", "", "");
        savedWorkRecommendList.setWorkQuestionRecommend(workQuestionRecommend);

        workRecommendListRepository.save(savedWorkRecommendList);
    }


    @Test
    @Transactional
    @Rollback(value = false)
    void q1_회사_이름_저장() {
        workQuestionRecommendService.saveRecommendCompanyNames(1L, "삼성전자, LG전자, 현대자동차");
        assertEquals("삼성전자, LG전자, 현대자동차"
                , workQuestionRecommendRepository.findById(1L)
                        .get().getRecommend_company_names()
        );
    }

    @Test
    void q2_회사_경력_저장() {
        workQuestionRecommendService.saveRecommendCompanyCareers(1L, "인사관리, 인재개발, 복지관리");
        assertEquals("인사관리, 인재개발, 복지관리", workQuestionRecommendRepository.findById(1L)
                .get().getRecommend_company_careers()
        );
    }

    @Test
    void q3_즐거움_저장() {
        workQuestionRecommendService.saveRecommendPrideJoy(1L, "사람들을 돕고 그들의 성장에 기여할 때 큰 뿌듯함을 느껴요. 특히, 제가 추천한 인재가 회사에서 성공적으로 자리잡는 모습을 볼 때 가장 기쁩니다.");
        assertEquals("사람들을 돕고 그들의 성장에 기여할 때 큰 뿌듯함을 느껴요. 특히, 제가 추천한 인재가 회사에서 성공적으로 자리잡는 모습을 볼 때 가장 기쁩니다."
                , workQuestionRecommendRepository.findById(1L)
                .get().getRecommend_pride_joy()
        );
    }

    @Test
    void q4_자격증_저장() {
        workQuestionRecommendService.saveRecommendCertifications(1L, "인사관리사, HRD전문가");
        assertEquals("인사관리사, HRD전문가"
                , workQuestionRecommendRepository.findById(1L)
                .get().getRecommend_certifications()
        );
    }



//        WorkQuestionRecommend workQuestionRecommend = new WorkQuestionRecommend(
//                "삼성전자, LG전자, 현대자동차",
//                "인사관리, 인재개발, 복지관리",
//                "인사관리사, HRD전문가",
//                "사람들을 돕고 그들의 성장에 기여할 때 큰 뿌듯함을 느껴요. 특히, 제가 추천한 인재가 회사에서 성공적으로 자리잡는 모습을 볼 때 가장 기쁩니다."
//        );

    @Test
    void q5_직종_추천() throws Exception {
        //given
        WorkQuestionRecommend workQuestionRecommend = new WorkQuestionRecommend(
                "00기업의 인사 관리 부서에서 근무했습니다. 주로 인재 채용, 교육 프로그램 개발,직원 복지 관리 등의 업무를 담당했어요.",
                "15년정도 됐어요",
                "‘인사 관리사’ 와 ‘HRD 전문가’ 자격증을 갖고 있어요.",
                "사람들을 돕고 그들의 성장에 기여할 때 큰 뿌듯함을 느껴요. 특히, 제가 추천한 인재가 회사에서 성공적으로 자리잡는 모습을 볼 때 가장 기쁩니다.");


        savedWorkRecommendList.setWorkQuestionRecommend(workQuestionRecommend);

        workQuestionRecommendRepository.save(workQuestionRecommend);
        //then

        String workQuestionForWorkField = workQuestionRecommendService.getWorkQuestionForWorkField(savedWorkRecommendList.getId());

        System.out.println("workQuestionForWorkField = " + workQuestionForWorkField);

    }




//    @Test
//    void q6_


    @Test
    void q8_최종_직업_보내주기() throws Exception {


        WorkQuestionRecommend workQuestionRecommend = WorkQuestionRecommend.builder()
                .recommend_company_names("00기업의 인사 관리 부서에서 근무했습니다. 주로 인재 채용, 교육 프로그램 개발,직원 복지 관리 등의 업무를 담당했어요.")
                .recommend_company_careers("15년정도 됐어요")
                .recommend_certifications("‘인사 관리사’ 와 ‘HRD 전문가’ 자격증을 갖고 있어요.")
                .recommend_pride_joy("사람들을 돕고 그들의 성장에 기여할 때 큰 뿌듯함을 느껴요. 특히, 제가 추천한 인재가 회사에서 성공적으로 자리잡는 모습을 볼 때 가장 기쁩니다.")
                .recommend_work_field("교육, 사회복지")
                .recommend_sigungu("부산광역시 해운대구 우2동에 거주하고 있어요.")
                .recommend_commute_hour("대중교통을 이용할 경우 30분 이내로 통근할 수 있으면 좋겠습니다. 자차를 이용할 경우에도 비슷한 시간대가 이상적입니다.")
//                .recommend_work_hour("하루에 4~6시간 정도 일하고 싶습니다. 일과 개인 생활의 균형을 유지하면서도 의미 있는 일을 하고 싶어요.")
                .build();

        System.out.println("workQuestionRecommend = " + workQuestionRecommend);

        savedWorkRecommendList.setWorkQuestionRecommend(workQuestionRecommend);

        workQuestionRecommendRepository.save(workQuestionRecommend);


        List<FinalJobSelectResponse> finalJobSelectResponses = workQuestionRecommendService.saveRecommendWorkHour(savedWorkRecommendList.getId(), "대중교통을 이용할 경우 30분 이내로 통근할 수 있으면 좋겠습니다. 자차를 이용할 경우에도 비슷한 시간대가 이상적입니다.");

        for (FinalJobSelectResponse finalJobSelectRespons : finalJobSelectResponses) {
            System.out.println("☮️ " + finalJobSelectRespons);
        }

//        savedWorkRecommendList.setWorkQuestionRecommend(workQuestionRecommend);
//
//        workQuestionRecommendRepository.save(workQuestionRecommend);
        //then

        String workQuestionForWorkField = workQuestionRecommendService.getWorkQuestionForWorkField(savedWorkRecommendList.getId());

        System.out.println("workQuestionForWorkField = " + workQuestionForWorkField);

    }



}