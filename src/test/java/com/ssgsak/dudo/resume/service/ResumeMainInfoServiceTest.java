package com.ssgsak.dudo.resume.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssgsak.dudo.resume.domain.ResumeMainInfo;
import com.ssgsak.dudo.resume.domain.ResumeQuestions;
import com.ssgsak.dudo.resume.request.ResumeMainInfoCreate;
import com.ssgsak.dudo.resume.request.ResumeMainInfoNameLocation;
import com.ssgsak.dudo.resume.request.ResumeMainInfoPhone;
import com.ssgsak.dudo.resume.request.ResumeMainInfoSchool;
import com.ssgsak.dudo.resume.response.CompanyHistoryResponse;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@Rollback(value = false)
class ResumeMainInfoServiceTest {

    @Autowired
    ResumeQuestionsService resumeQuestionsService;

    @Autowired
    ResumeMainInfoService resumeMainInfoService;

    @Autowired
    EntityManager em;

    @Test
    void test1() throws Exception{
        // given
        // ResumeQuestions 만들기
        ResumeQuestions findQuestion = resumeQuestionsService.findOne(createResumeQuestion());
        // 아까 만든 ResumeQuestions를 찾아서 ResumeMainInfo 만들기
        createResumeMainInfo(findQuestion.getId());

        //when
        ResumeQuestions one = resumeQuestionsService.findOne(findQuestion.getId());
        System.out.println("one = " + one.getResume_title());
        System.out.println("one.getResumeMainInfo().getResume_name() = " + one.getResumeMainInfoList().get(0));
        //then
    }

    @Test
    @DisplayName("ResumeQuestions를 찾아서 이름, 생년월일, 주소로 ResumeMainInfo 만들기")
    void test2() throws Exception{
        //given
        // ResumeQuestions 만들기
        ResumeQuestions findQuestion = resumeQuestionsService.findOne(createResumeQuestion());
        // 아까 만든 ResumeQuestions를 찾아서 ResumeMainInfo 만들기
        createResumeMainInfoWithName(findQuestion.getId(), "테스트 이력서 1", "1993-01-01", "서울시 강남구");
        // 전화번호, 이메일 추가
        ResumeMainInfo findMainInfo = resumeMainInfoService.findOne(findQuestion.getId());
        ResumeMainInfoPhone resumeMainInfoPhone = resumeMainInfoService.saveResumeforPhone(findMainInfo.getId(), new ResumeMainInfoPhone("010-1234-5678", "juna1234@naver.com"));
        assertEquals(resumeMainInfoPhone.getResume_phone(), "010-1234-5678");
        //when
    }

    @Test
    @DisplayName("ResumeQuestions를 찾아서 학교, 전공 넣은 ResumeMainInfo 만들기")
    void test3() throws Exception{
        //given
        // ResumeQuestions 만들기
        ResumeQuestions findQuestion = resumeQuestionsService.findOne(createResumeQuestion());
        // 아까 만든 ResumeQuestions를 찾아서 ResumeMainInfo 만들기
        createResumeMainInfoWithName(findQuestion.getId(), "테스트 이력서 1", "1993-01-01", "서울시 강남구");
        // 전화번호, 이메일 추가
        ResumeMainInfo findMainInfo = resumeMainInfoService.findOne(findQuestion.getId());
        resumeMainInfoService.saveResumeforPhone(findMainInfo.getId(), new ResumeMainInfoPhone("010-1234-5678", ";"));

        // 학교, 전공 추가
        ResumeMainInfoSchool resumeMainInfo = resumeMainInfoService.saveResumeForSchool(findMainInfo.getId(), "저는 서울대학교에서 컴퓨터공학과를 졸업하고 고려대학원에서 정보통신공학을 전공했습니다.");
        System.out.println("resumeMainInfo.getResume_school() = " + resumeMainInfo.getResume_school());

    }


    @Test
    @DisplayName("ResumeQuestions를 찾아서 학교, 전공 넣은 ResumeMainInfo 만들기")
    void test4() throws Exception{
        //given
        // ResumeQuestions 만들기
        ResumeQuestions findQuestion = resumeQuestionsService.findOne(createResumeQuestion());
        // 아까 만든 ResumeQuestions를 찾아서 ResumeMainInfo 만들기
        createResumeMainInfoWithName(findQuestion.getId(), "테스트 이력서 1", "1993-01-01", "서울시 강남구");
        // 전화번호, 이메일 추가
        ResumeMainInfo findMainInfo = resumeMainInfoService.findOne(findQuestion.getId());
        resumeMainInfoService.saveResumeforPhone(findMainInfo.getId(), new ResumeMainInfoPhone("010-1234-5678", ";"));

        // 학교, 전공 추가
        resumeMainInfoService.saveResumeForSchool(findMainInfo.getId(), "저는 서울대학교에서 컴퓨터공학과를 졸업하고 고려대학원에서 정보통신공학을 전공했습니다.");

        // companyHistory 추가
        List<CompanyHistoryResponse> companyHistory = createCompanyHistory(findMainInfo.getId(), "저는 카카오에서 3년간 근무했습니다. 주로 인공지능 개발 업무를 담당했어요. 그리고 현재는 네이버에서 근무하고 있습니다.");
        for (CompanyHistoryResponse companyHistoryResponse : companyHistory) {
            System.out.print("회사 1 : ");
            System.out.println(companyHistoryResponse);
        }

    }

    @Test
    @DisplayName("이력서 작성 6번째 질문 - 자격증, 교육 관련 질문")
    void test5() throws Exception{
        //given

        //when

        //then
    }


    /**
     * 회사를 저장하는 메서드
     */
    private List<CompanyHistoryResponse> createCompanyHistory(Long id, String request) throws JsonProcessingException {
        return resumeMainInfoService.saveResumeForCompanyHistory(id, request);
    }

    /**
     * 이름, 생년월일, 주소를 가진 이력서를 저장하는 메서드
     */
    private ResumeMainInfoNameLocation createResumeMainInfoWithName(Long id, String resumeName, String resumeDate, String resumeAddress) {
        ResumeMainInfoNameLocation request = ResumeMainInfoNameLocation.builder()
                .resume_name(resumeName)
                .resume_birth(LocalDate.parse(resumeDate))
                .resume_address(resumeAddress)
                .build();
        return resumeMainInfoService.saveResumeForNameLocation(id, request);
    }





    /**
     * createResumeQuestion 메서드를 호출하여 테스트 데이터를 생성합니다.
     */
    private Long createResumeQuestion() {
        ResumeQuestions resumeQuestions = new ResumeQuestions("테스트 입니다!!!");
        return resumeQuestionsService.save(resumeQuestions);
    }

    /**
     * createResumeQuestion 메서드를 호출하여 테스트 데이터를 생성합니다.
     */
    private void createResumeMainInfo(Long resumeQuestionId) {
        ResumeMainInfoCreate resumeMainInfoCreate = ResumeMainInfoCreate.builder()
                .resume_name("테스트 이력서 1")
                .resume_birth(LocalDate.parse("1993-01-01"))
                .resume_address("서울시 강남구")
                .resume_email("juna1234@naver.com")
                .resume_phone("010-1234-5678")
                .resume_school("서울대학교")
                .resume_major("컴퓨터공학과")
                .build();

        resumeMainInfoService.createResumeMainInfo(resumeQuestionId, resumeMainInfoCreate);
    }

}