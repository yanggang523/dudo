package com.ssgsak.dudo.resume.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssgsak.dudo.resume.domain.CompanyHistory;
import com.ssgsak.dudo.resume.domain.EtcHistory;
import com.ssgsak.dudo.resume.domain.ResumeMainInfo;
import com.ssgsak.dudo.resume.domain.ResumeQuestions;
import com.ssgsak.dudo.resume.repository.CompanyHistoryRepository;
import com.ssgsak.dudo.resume.repository.EtcHistoryRepository;
import com.ssgsak.dudo.resume.repository.ResumeMainInfoRepository;
import com.ssgsak.dudo.resume.repository.ResumeQuestionsRepository;
import com.ssgsak.dudo.resume.request.*;
import com.ssgsak.dudo.resume.response.CompanyHistoryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ResumeMainInfoService {

    private final ResumeQuestionsRepository resumeQuestionsRepository;
    private final ResumeMainInfoRepository resumeMainInfoRepository;
    private final EtcHistoryRepository etcHistoryRepository;

    private final CompanyHistoryRepository companyHistoryRepository;

    // ai 서비스
    private final ResumeQuestionOpenAiService resumeQuestionOpenAiService;


    // 연습용
    public void createResumeMainInfo(Long resumeQuestionsId, ResumeMainInfoCreate resumeMainInfoCreate) {

        ResumeQuestions resumeQuestions = resumeQuestionsRepository.findById(resumeQuestionsId).orElseThrow();

        ResumeMainInfo resumeMainInfo = ResumeMainInfo.builder()
                .resume_name(resumeMainInfoCreate.getResume_name())
                .resume_birth(resumeMainInfoCreate.getResume_birth())
                .resume_address(resumeMainInfoCreate.getResume_address())
                .resume_email(resumeMainInfoCreate.getResume_email())
                .resume_phone(resumeMainInfoCreate.getResume_phone())
                .resume_school(resumeMainInfoCreate.getResume_school())
                .resume_major(resumeMainInfoCreate.getResume_major())
                .build();


        resumeQuestions.addMainInfo(resumeMainInfo);

        resumeMainInfoRepository.save(resumeMainInfo);

    }


    // q1 - 이력서 작성 :  이름, 생년월일, 주소 저장
    public ResumeMainInfoNameLocation saveResumeForNameLocation(Long resumeQuestionsId, ResumeMainInfoNameLocation resumeMainInfoCreate) {

        ResumeQuestions resumeQuestions = resumeQuestionsRepository.findById(resumeQuestionsId).orElseThrow();


        ResumeMainInfo resumeMainInfo = ResumeMainInfo.builder()
                .resume_name(resumeMainInfoCreate.getResume_name())
                .resume_birth(resumeMainInfoCreate.getResume_birth())
                .resume_address(resumeMainInfoCreate.getResume_address())
                .build();

        resumeQuestions.addMainInfo(resumeMainInfo);

        resumeMainInfoRepository.save(resumeMainInfo);
        return resumeMainInfoCreate;
    }

    // q2 - 이력서 작성 연락처, 이메일 작성
    public ResumeMainInfoPhone saveResumeForPhone(Long resumeMainInfoId, ResumeMainInfoPhone resumeMainInfoCreate) {

        ResumeMainInfo resumeMainInfo = resumeMainInfoRepository.findById(resumeMainInfoId).orElseThrow();
        resumeMainInfo.changeResumePhone(resumeMainInfoCreate.getResume_phone(), resumeMainInfoCreate.getResume_email());

        return resumeMainInfoCreate;
    }

    // q3 - 이력서 작성 : 학교, 전공 작성
    public ResumeMainInfoSchool saveResumeForSchool(Long resumeMainInfoId, String request) throws JsonProcessingException {

        ResumeMainInfo resumeMainInfo = resumeMainInfoRepository.findById(resumeMainInfoId).orElseThrow();

        ResumeMainInfoSchool resumeSchoolWithAi = resumeQuestionOpenAiService.getResumeSchoolWithAi(request);

        resumeMainInfo.changeResumeSchool(resumeSchoolWithAi.getResume_school(), resumeSchoolWithAi.getResume_major());

        return resumeSchoolWithAi;

    }

    // q4 - 이력서 작성 : 근무한 회사이름, 근속연수, 했던일 저장
    public List<CompanyHistoryResponse> saveResumeForCompanyHistory(Long resumeMainInfoId, String request) throws JsonProcessingException {

        ResumeMainInfo resumeMainInfo = resumeMainInfoRepository.findById(resumeMainInfoId).orElseThrow();

        List<CompanyHistoryResponse> resumeCompanyHistoryWithAi = resumeQuestionOpenAiService.getResumeCompanyHistoryWithAi(request);

        for (CompanyHistoryResponse companyHistoryResponse : resumeCompanyHistoryWithAi) {
            String companyName = companyHistoryResponse.getResume_company();
            int companyYear = companyHistoryResponse.getResume_service_year();
            String companyContent = companyHistoryResponse.getResume_job_responsibilities();
            CompanyHistory company = CompanyHistory.builder()
                    .resume_company(companyName)
                    .resume_service_year(companyYear)
                    .resume_job_responsibilities(companyContent).build();

            CompanyHistory savedCompany = companyHistoryRepository.save(company);
            resumeMainInfo.addCompanyHistory(savedCompany);
        }

        return resumeCompanyHistoryWithAi;

    }

    /**
     * q5 - 이력서 작성 : 언어 사용기술 저장 (new EtcHistory())
     */
    public ResumeMainInfoSkillLanguage saveResumeForSkillLanguage(Long resumeMainInfoId, String request) {

        ResumeMainInfo resumeMainInfo = resumeMainInfoRepository.findById(resumeMainInfoId).orElseThrow();

        ResumeMainInfoSkillLanguage resumeCompanyHistoryWithAi = resumeQuestionOpenAiService.getResumeSkillLanguages(request);
        resumeMainInfo.changeResume_skill_language(resumeCompanyHistoryWithAi.getResume_skills(), resumeCompanyHistoryWithAi.getResume_languages());

        return resumeCompanyHistoryWithAi;
    }

    // q6 - 이력서 작성 :  자격증, 교육 관련 질문 저장
    public ResumeMainInfoCertificationEducation saveResumeForCertificationEducation(Long resumeMainInfoId, String request) {

        ResumeMainInfo resumeMainInfo = resumeMainInfoRepository.findById(resumeMainInfoId).orElseThrow();

        ResumeMainInfoCertificationEducation resumeCompanyHistoryWithAi = resumeQuestionOpenAiService.getResumeCertificationEducation(request);

        EtcHistory etcHistory = resumeMainInfo.getEtcHistory();

        etcHistory.changeResume_certification_education(resumeCompanyHistoryWithAi.getResume_certificates(), resumeCompanyHistoryWithAi.getResume_education());

        return resumeCompanyHistoryWithAi;
    }

    // q7 - 이력서 작성 : 수상 관련 질문
    public String saveResumeForAward(Long resumeMainInfoId, String request) {

        ResumeMainInfo resumeMainInfo = resumeMainInfoRepository.findById(resumeMainInfoId).orElseThrow();

        String award = resumeQuestionOpenAiService.getResumeAward(request);

        EtcHistory etcHistory = resumeMainInfo.getEtcHistory();

        etcHistory.changeResume_award(award);

        return award;
    }



    // q8 - 이력서 작성 : 봉사
    public String saveResumeForVolnteer(Long resumeMainInfoId, String request) {

        ResumeMainInfo resumeMainInfo = resumeMainInfoRepository.findById(resumeMainInfoId).orElseThrow();
        resumeQuestionOpenAiService.getResumeVolunteer(request);
//        String project = resumeQuestionOpenAiService.getResumeProject(request);

        return "";
    }

//    @Transactional(readOnly = true)
    public ResumeMainInfo findOne(Long id) {
        return resumeMainInfoRepository.findById(id).orElse(null);
    }

    public List<ResumeMainInfo> findAll() {
        return resumeMainInfoRepository.findAll();
    }

    public void delete(Long id) {
        resumeMainInfoRepository.deleteById(id);
    }


}
