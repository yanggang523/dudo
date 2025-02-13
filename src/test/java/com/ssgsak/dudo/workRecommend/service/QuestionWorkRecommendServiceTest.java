package com.ssgsak.dudo.workRecommend.service;

import com.ssgsak.dudo.workRecommend.domain.QuestionWorkRecommend;
import com.ssgsak.dudo.workRecommend.domain.UserWorkRecommend;
import com.ssgsak.dudo.workRecommend.repository.UserWorkRecommendRepository;
import jakarta.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Rollback(value = false)
@Transactional
@SpringBootTest
class QuestionWorkRecommendServiceTest {

    @Autowired
    UserWorkRecommendRepository userWorkRecommendRepository;
    @Autowired
    com.ssgsak.dudo.workRecommend.repository.questionWorkRecommendRepository questionWorkRecommendRepository;


    @Autowired
    QuestionWorkRecommendService questionWorkRecommendService;


    @Autowired
    EntityManager em;


    // 테스트용 데이터
//    WorkRecommendList savedWorkRecommendList;


    @Test
    void clear() {
        questionWorkRecommendRepository.deleteAll();
        userWorkRecommendRepository.deleteAll();
    }

//    @Test
//    void 유저_생성() {
//        UserWorkRecommend madeUserWorkRecommend = createWorkRecommendList();
//        QuestionWorkRecommend questionWorkRecommend = new QuestionWorkRecommend("", "", "", "");
//        madeUserWorkRecommend.setQuestionWorkRecommend(questionWorkRecommend);
//
//    }


    @Test
    void q1_회사_이름_저장() {
        UserWorkRecommend userWorkRecommend = createWorkRecommendList();
        QuestionWorkRecommend questionWorkRecommend = createWorkQuestionRecommend(userWorkRecommend);
        questionWorkRecommendService.saveRecommendCompanyNames(questionWorkRecommend.getId(), "삼성전자, LG전자, 현대자동차");

        assertEquals("삼성전자, LG전자, 현대자동차",
                questionWorkRecommendRepository.findById(1L).get().getRecommend_company_names());

    }


    @Test
    void q2_회사_경력_저장() {
        UserWorkRecommend userWorkRecommend = createWorkRecommendList();
        QuestionWorkRecommend questionWorkRecommend = createWorkQuestionRecommend(userWorkRecommend);
        questionWorkRecommendService.saveRecommendCompanyNames(questionWorkRecommend.getId(), "삼성전자, LG전자, 현대자동차");

        questionWorkRecommendService.saveRecommendCompanyCareers(questionWorkRecommend.getId(), "인사관리, 인재개발, 복지관리");
        assertEquals("인사관리, 인재개발, 복지관리", questionWorkRecommendRepository.findById(1L)
                .get().getRecommend_company_careers()
        );
    }

    @Test
    void q3_자격증_저장() {
        questionWorkRecommendService.saveRecommendCertifications(1L, "인사관리사, HRD전문가");
        assertEquals("인사관리사, HRD전문가", questionWorkRecommendRepository.findById(1L)
                .get().getRecommend_certifications()
        );
    }


    @Test
    void q4_즐거움_저장() {
        UserWorkRecommend userWorkRecommend = createWorkRecommendList();
        QuestionWorkRecommend questionWorkRecommend = createWorkQuestionRecommend(userWorkRecommend);
        questionWorkRecommendService.saveRecommendCompanyNames(questionWorkRecommend.getId(), "삼성전자, LG전자, 현대자동차");

        questionWorkRecommendService.saveRecommendPrideJoy(1L, "사람들을 돕고 그들의 성장에 기여할 때 큰 뿌듯함을 느껴요. 특히, 제가 추천한 인재가 회사에서 성공적으로 자리잡는 모습을 볼 때 가장 기쁩니다.");
        assertEquals("사람들을 돕고 그들의 성장에 기여할 때 큰 뿌듯함을 느껴요. 특히, 제가 추천한 인재가 회사에서 성공적으로 자리잡는 모습을 볼 때 가장 기쁩니다."
                , questionWorkRecommendRepository.findById(1L).get().getRecommend_pride_joy()
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
        QuestionWorkRecommend questionWorkRecommend = new QuestionWorkRecommend(
                "00기업의 인사 관리 부서에서 근무했습니다. 주로 인재 채용, 교육 프로그램 개발,직원 복지 관리 등의 업무를 담당했어요.",
                "15년정도 됐어요",
                "‘인사 관리사’ 와 ‘HRD 전문가’ 자격증을 갖고 있어요.",
                "사람들을 돕고 그들의 성장에 기여할 때 큰 뿌듯함을 느껴요. 특히, 제가 추천한 인재가 회사에서 성공적으로 자리잡는 모습을 볼 때 가장 기쁩니다.");


//        savedWorkRecommendList.setWorkQuestionRecommend(workQuestionRecommend);

        questionWorkRecommendRepository.save(questionWorkRecommend);
        //then

//        List<WorkFieldListResponse> workQuestionForWorkField = workQuestionRecommendService.getWorkQuestionForWorkField(savedWorkRecommendList.getId());

//        System.out.println("workQuestionForWorkField = " + workQuestionForWorkField);

    }



//    @Test
//    void q6_

    @Test
    void q8_최종_직업_보내주기() throws Exception {


        QuestionWorkRecommend questionWorkRecommend = QuestionWorkRecommend.builder()
                .recommend_company_names("00기업의 인사 관리 부서에서 근무했습니다. 주로 인재 채용, 교육 프로그램 개발,직원 복지 관리 등의 업무를 담당했어요.")
                .recommend_company_careers("15년정도 됐어요")
                .recommend_certifications("‘인사 관리사’ 와 ‘HRD 전문가’ 자격증을 갖고 있어요.")
                .recommend_pride_joy("사람들을 돕고 그들의 성장에 기여할 때 큰 뿌듯함을 느껴요. 특히, 제가 추천한 인재가 회사에서 성공적으로 자리잡는 모습을 볼 때 가장 기쁩니다.")
                .recommend_work_field("교육, 사회복지")
                .recommend_sigungu("부산광역시 해운대구 우2동에 거주하고 있어요.")
                .recommend_commute_hour("대중교통을 이용할 경우 30분 이내로 통근할 수 있으면 좋겠습니다. 자차를 이용할 경우에도 비슷한 시간대가 이상적입니다.")
//                .recommend_work_hour("하루에 4~6시간 정도 일하고 싶습니다. 일과 개인 생활의 균형을 유지하면서도 의미 있는 일을 하고 싶어요.")
                .build();

        System.out.println("workQuestionRecommend = " + questionWorkRecommend);

//        savedWorkRecommendList.setWorkQuestionRecommend(workQuestionRecommend);

        questionWorkRecommendRepository.save(questionWorkRecommend);


//        List<FinalJobSelectResponse> finalJobSelectResponses = workQuestionRecommendService.saveRecommendWorkHour(savedWorkRecommendList.getId(), "대중교통을 이용할 경우 30분 이내로 통근할 수 있으면 좋겠습니다. 자차를 이용할 경우에도 비슷한 시간대가 이상적입니다.");

//        for (FinalJobSelectResponse finalJobSelectRespons : finalJobSelectResponses) {
//            System.out.println("☮️ " + finalJobSelectRespons);
//        }

//        savedWorkRecommendList.setWorkQuestionRecommend(workQuestionRecommend);
//
//        workQuestionRecommendRepository.save(workQuestionRecommend);
        //then

//        List<WorkFieldListResponse> workQuestionForWorkField = workQuestionRecommendService.getWorkQuestionForWorkField(savedWorkRecommendList.getId());
//        for (WorkFieldListResponse workFieldListResponse : workQuestionForWorkField) {
//            System.out.println("workFieldListResponse = " + workFieldListResponse);
//        }
    }


    /**
     * 메서드를 호출하여 WorkRecommendList 데이터를 생성합니다.
     */
    private UserWorkRecommend createWorkRecommendList() {
        UserWorkRecommend userWorkRecommend = new UserWorkRecommend();
        return userWorkRecommendRepository.save(userWorkRecommend);
    }

    private QuestionWorkRecommend createWorkQuestionRecommend(UserWorkRecommend userWorkRecommend) {
        QuestionWorkRecommend questionWorkRecommend = new QuestionWorkRecommend(userWorkRecommend);
        return questionWorkRecommendRepository.save(questionWorkRecommend);
    }

}