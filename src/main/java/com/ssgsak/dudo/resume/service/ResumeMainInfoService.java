package com.ssgsak.dudo.resume.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssgsak.dudo.resume.domain.CompanyHistory;
import com.ssgsak.dudo.resume.domain.ResumeMainInfo;
import com.ssgsak.dudo.resume.domain.ResumeQuestions;
import com.ssgsak.dudo.resume.repository.CompanyHistoryRepository;
import com.ssgsak.dudo.resume.repository.ResumeMainInfoRepository;
import com.ssgsak.dudo.resume.repository.ResumeQuestionsRepository;
import com.ssgsak.dudo.resume.request.ResumeMainInfoCreate;
import com.ssgsak.dudo.resume.request.ResumeMainInfoNameLocation;
import com.ssgsak.dudo.resume.request.ResumeMainInfoPhone;
import com.ssgsak.dudo.resume.request.ResumeMainInfoSchool;
import com.ssgsak.dudo.resume.response.CompanyHistoryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ResumeMainInfoService {

    private final ResumeQuestionsRepository resumeQuestionsRepository;
    private final ResumeMainInfoRepository resumeMainInfoRepository;

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



    // 이력서 작성 이름, 생년월일, 주소 저장
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

    // 이력서 작성 전화번호, 이메일 작성
    public ResumeMainInfoPhone saveResumeforPhone(Long resumeMainInfoId, @Valid ResumeMainInfoPhone resumeMainInfoCreate) {

        ResumeMainInfo resumeMainInfo = resumeMainInfoRepository.findById(resumeMainInfoId).orElseThrow();
        resumeMainInfo.changeResumePhone(resumeMainInfoCreate.getResume_phone(), resumeMainInfoCreate.getResume_email());

        return resumeMainInfoCreate;
    }

    // 이력서 작성 : 학교, 전공 작성
    public ResumeMainInfoSchool saveResumeForSchool(Long resumeMainInfoId, String request) throws JsonProcessingException {

        ResumeMainInfo resumeMainInfo = resumeMainInfoRepository.findById(resumeMainInfoId).orElseThrow();

        ResumeMainInfoSchool resumeSchoolWithAi = resumeQuestionOpenAiService.getResumeSchoolWithAi(request);

        resumeMainInfo.changeResumeSchool(resumeSchoolWithAi.getResume_school(), resumeSchoolWithAi.getResume_major());

        return resumeSchoolWithAi;

    }

    // 이력서 작성 : 근무한 회사이름, 근속연수, 했던일 저장
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

    public Long save(ResumeMainInfo resumeMainInfo) {
        ResumeMainInfo save = resumeMainInfoRepository.save(resumeMainInfo);
        return save.getId();
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
