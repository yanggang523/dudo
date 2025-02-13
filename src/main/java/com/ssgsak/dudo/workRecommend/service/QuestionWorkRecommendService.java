package com.ssgsak.dudo.workRecommend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssgsak.dudo.workRecommend.domain.QuestionWorkRecommend;
import com.ssgsak.dudo.workRecommend.domain.UserWorkRecommend;
import com.ssgsak.dudo.workRecommend.repository.JobRecommendResultRepository;
import com.ssgsak.dudo.workRecommend.repository.UserWorkRecommendRepository;
import com.ssgsak.dudo.workRecommend.request.WorkFieldRequestForAi;
import com.ssgsak.dudo.workRecommend.response.WorkFieldListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class QuestionWorkRecommendService {

    // q1~q8 저장하는 질문 리포지토리
    private final com.ssgsak.dudo.workRecommend.repository.questionWorkRecommendRepository questionWorkRecommendRepository;
    //
    private final UserWorkRecommendRepository userWorkRecommendRepository;

    private final JobRecommendResultRepository jobRecommendResultRepository;

    // ai 서비스
    private final WorkQuestionOpenAiService workQuestionOpenAiService;


    // q1 이전 회사 이름 저장
    public void saveRecommendCompanyNames(Long workRecommendListId, String recommendCompanyNames) {
        UserWorkRecommend userWorkRecommend = userWorkRecommendRepository.findById(workRecommendListId).orElseThrow();
        QuestionWorkRecommend questionWorkRecommend = (QuestionWorkRecommend) questionWorkRecommendRepository.findByUserWorkRecommend(userWorkRecommend).orElseThrow();
        questionWorkRecommend.setRecommend_company_names(recommendCompanyNames);
    }

    // q2 이전 직업 경력 저장
    public void saveRecommendCompanyCareers(Long workRecommendListId, String recommendCompanyCareers) {
        UserWorkRecommend userWorkRecommend = userWorkRecommendRepository.findById(workRecommendListId).orElseThrow();
        QuestionWorkRecommend questionWorkRecommend = (QuestionWorkRecommend) questionWorkRecommendRepository.findByUserWorkRecommend(userWorkRecommend).orElseThrow();
        questionWorkRecommend.setRecommend_company_careers(recommendCompanyCareers);

    }

    // q3 이전 자격증 저장
    public void saveRecommendCertifications(Long workRecommendListId, String recommendCertifications) {
        UserWorkRecommend userWorkRecommend = userWorkRecommendRepository.findById(workRecommendListId).orElseThrow();
        QuestionWorkRecommend questionWorkRecommend = (QuestionWorkRecommend) questionWorkRecommendRepository.findByUserWorkRecommend(userWorkRecommend).orElseThrow();
        questionWorkRecommend.setRecommend_certifications(recommendCertifications);

    }

    // q4 즐거움과 적성 저장
    public void saveRecommendPrideJoy(Long workRecommendListId, String recommendPrideJoy) {
        UserWorkRecommend userWorkRecommend = userWorkRecommendRepository.findById(workRecommendListId).orElseThrow();
        QuestionWorkRecommend questionWorkRecommend = (QuestionWorkRecommend) questionWorkRecommendRepository.findByUserWorkRecommend(userWorkRecommend).orElseThrow();
        questionWorkRecommend.setRecommend_pride_joy(recommendPrideJoy);

    }


    // q5 AI를 통해 추천된 업무분야 가져오기
    public List<WorkFieldListResponse> getWorkQuestionForWorkField(Long workRecommendListId) throws JsonProcessingException {

        UserWorkRecommend userWorkRecommend = userWorkRecommendRepository.findById(workRecommendListId).orElseThrow();
        QuestionWorkRecommend questionRecommend = (QuestionWorkRecommend) questionWorkRecommendRepository.findByUserWorkRecommend(userWorkRecommend).orElseThrow();
        WorkFieldRequestForAi workFieldRequestForAi = WorkFieldRequestForAi.builder()
                .recommend_company_names(questionRecommend.getRecommend_company_names())
                .recommend_company_careers(questionRecommend.getRecommend_company_careers())
                .recommend_certifications(questionRecommend.getRecommend_certifications())
                .recommend_pride_joy(questionRecommend.getRecommend_pride_joy())
                .build();



        return workQuestionOpenAiService.getWorkFieldWithAi(workFieldRequestForAi);

    }


    // q8 근무시간 저장
//    public List<FinalJobSelectResponse> saveRecommendWorkHour(Long workRecommendListId, String recommendWorkHour) throws JsonProcessingException {
//        UserWorkRecommend userWorkRecommend = workRecommendListRepository.findById(workRecommendListId).orElseThrow();
//
//
//        QuestionWorkRecommend questionWorkRecommend = (QuestionWorkRecommend) workQuestionRecommendRepository.findByWorkRecommendList(userWorkRecommend).orElseThrow();
//        questionWorkRecommend.setRecommend_work_hour(recommendWorkHour);
//
//        FinalJobSelectRequest request = FinalJobSelectRequest.builder()
//                .recommend_company_names(questionWorkRecommend.getRecommend_company_names())
//                .recommend_company_careers(questionWorkRecommend.getRecommend_company_careers())
//                .recommend_certifications(questionWorkRecommend.getRecommend_certifications())
//                .recommend_pride_joy(questionWorkRecommend.getRecommend_pride_joy())
//                .recommend_sigungu(questionWorkRecommend.getRecommend_sigungu())
//                .recommend_commute_hour(questionWorkRecommend.getRecommend_commute_hour())
//                .recommend_work_hour(recommendWorkHour)
//                .recommend_work_field(questionWorkRecommend.getRecommend_work_field())
//                .build();
//
//        List<JobRecommendResult> jobRecommendResults = jobRecommendResultRepository.findAll();
//        List<FinalJobSelectResponse> list = workQuestionOpenAiService.getFinalJobSelectResponse(request);
//        for (FinalJobSelectResponse finalJobSelectResponse : list) {
//            JobRecommendResult build = JobRecommendResult.builder()
//                    .workRecommendList(userWorkRecommend)
//                    .rank(finalJobSelectResponse.getRank())
//                    .recommend_job_name(finalJobSelectResponse.getRecommend_job_name())
//                    .recommend_job_url1(finalJobSelectResponse.getRecommend_job_url1())
//                    .recommend_job_url2(finalJobSelectResponse.getRecommend_job_url2())
//                    .recommend_job_url3(finalJobSelectResponse.getRecommend_job_url3())
//                    .build();
//
//            jobRecommendResultRepository.save(build);
//        }
//
//        return list;
//    }

    // q5 원하는 업무분야 저장
    public void saveRecommendWorkField(Long workRecommendListId, String recommendWorkField) {
        UserWorkRecommend userWorkRecommend = userWorkRecommendRepository.findById(workRecommendListId).orElseThrow();
        QuestionWorkRecommend questionWorkRecommend = (QuestionWorkRecommend) questionWorkRecommendRepository.findByUserWorkRecommend(userWorkRecommend).orElseThrow();
        questionWorkRecommend.setRecommend_work_field(recommendWorkField);
    }

    // q6 시군구 저장
    public void saveRecommendSigungu(Long workRecommendListId, String recommendSigungu) {
        UserWorkRecommend userWorkRecommend = userWorkRecommendRepository.findById(workRecommendListId).orElseThrow();
        QuestionWorkRecommend questionWorkRecommend = (QuestionWorkRecommend) questionWorkRecommendRepository.findByUserWorkRecommend(userWorkRecommend).orElseThrow();
        questionWorkRecommend.setRecommend_sigungu(recommendSigungu);
    }


    // q7 통근시간 저장
    public void saveRecommendCommuteHour(Long workRecommendListId, String recommendCommuteHour) {
        UserWorkRecommend userWorkRecommend = userWorkRecommendRepository.findById(workRecommendListId).orElseThrow();
        QuestionWorkRecommend questionWorkRecommend = (QuestionWorkRecommend) questionWorkRecommendRepository.findByUserWorkRecommend(userWorkRecommend).orElseThrow();
        questionWorkRecommend.setRecommend_commute_hour(recommendCommuteHour);
    }

    // 최종 직업 저장


}
