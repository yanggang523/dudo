package com.ssgsak.dudo.resume.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssgsak.dudo.config.OpenAIService;
import com.ssgsak.dudo.resume.request.ResumeMainInfoSchool;
import com.ssgsak.dudo.resume.response.CompanyHistoryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResumeQuestionOpenAiService {

    private final ObjectMapper objectMapper;
    private final OpenAIService openAIService;


    @Transactional
    public ResumeMainInfoSchool getResumeSchoolWithAi(String request) throws JsonProcessingException {

        String systemMessage = """
                    당신은 AI 기반 이력서 추천 시스템입니다.
                    사용자의 학력 정보를 추출해야 합니다.
                    입력 : 저는 부산대학교에서 사회복지학과를 전공했습니다.
                    출력 : {
                              "resume_school": "부산대학교",
                              "resume_major": "사회복지학과"
                            }
                
                    학교 이름은 간결하고 명확해야 하며, 전공과 학위는 이해하기 쉽게 작성해야 합니다.
                    JSON 외의 다른 응답은 금지됩니다.
                """;

        String response = openAIService.getChatGPTResponse(systemMessage, request);
        log.info("response = {}", response);

        return objectMapper.readValue(response, ResumeMainInfoSchool.class);

    }


    public List<CompanyHistoryResponse> getResumeCompanyHistoryWithAi(String request) throws JsonProcessingException {
        String systemMessage = """
                당신은 이력서에서 핵심 키워드를 추출하는 AI입니다.
                주어진 문장에서 중요한 회사명, 직무, 업무 내용과 관련된 키워드만 추출하세요.
                키워드는 짧고 명확해야 하며, 리스트 형식으로 제공해야 합니다.
                요청 형식 예시:
                {
                 "text": "저는 삼성전자에서 3년간 소프트웨어 엔지니어로 근무했습니다. 주로 자바와 파이썬을 사용하여 웹 개발을 담당했습니다. 또한, 데이터베이스 설계와 관리 업무를 수행했습니다.
                 또 10년간 하이닉스에 근무했습니다. 하드웨어 엔지니어로 일하며, 반도체 제조 공정을 담당했습니다."
                
                }
                응답 형식 예시:
                [
                    {
                        "resume_company": "삼성전자",
                        "resume_service_year": 3,
                        "resume_job_responsibilities": "소프트웨어 엔지니어, 자바, 파이썬, 웹 개발, 데이터베이스 설계, 관리"
                    },
                       {
                        "resume_company": "하이닉스",
                        "resume_service_year": 10,
                        "resume_job_responsibilities": "하드웨어 엔지니어, 반도체 제조 공정"
                    }
                ]
                문장을 입력하면, JSON 형식으로만 응답하세요.
                """;

        // OpenAI API 호출 (jsonResponse는 위 시스템 메시지에 따른 응답 JSON 문자열)
        String jsonResponse = openAIService.getChatGPTResponse(systemMessage, request);

        // ObjectMapper를 사용해 JSON 배열을 List<CompanyHistoryResponse>로 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        List<CompanyHistoryResponse> companyHistoryList = objectMapper.readValue(
                jsonResponse, new TypeReference<List<CompanyHistoryResponse>>() {
                }
        );

        return companyHistoryList;
    }


}