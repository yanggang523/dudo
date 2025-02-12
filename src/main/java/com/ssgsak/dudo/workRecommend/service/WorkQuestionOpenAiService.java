package com.ssgsak.dudo.workRecommend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssgsak.dudo.config.OpenAIService;
import com.ssgsak.dudo.workRecommend.request.FinalJobSelectRequest;
import com.ssgsak.dudo.workRecommend.request.WorkFieldRequestForAi;
import com.ssgsak.dudo.workRecommend.response.FinalJobSelectResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkQuestionOpenAiService {

    private final ObjectMapper objectMapper;
    private final OpenAIService openAIService;


    @Transactional
    public String getWorkFieldWithAi(WorkFieldRequestForAi request) throws JsonProcessingException {

        String userMessage = objectMapper.writeValueAsString(request);
        String systemMessage = """
                    당신은 AI 기반 직종 추천 시스템입니다.
                    사용자의 회사명, 경력, 자격증, 직업에서 느끼는 보람과 즐거움 정보를 기반으로 적절한 직종 6개를 추천해야 합니다.
                    직종 종류에는
                    [행정, 경영, 금융, 보험, 교육, 법률, 복지, 의료, 예술, 방송, 정보통신, 미용, 여행, 숙박, 식음료, 영업, 판매, 운송, 건설, 채굴, 제조, 생산, 회계 및 경리, 광고, 무역, 운송, 자재, 사무, 사무보조, 안내 및 접수, 고객상담, 통계, 컴퓨터하드웨어, 소프트웨어, 정보보안, 기계공학, 로봇공학, 전기전자, 섬유, 식품, 강사, 사회복지, 상담, 보육, 반려동물, 예식, 오락, 조리, 식당, 경비, 돌봄, 청소, 방역, 검침, 중개, 작물재배, 낙농 및 사육, 임업, 어업]이 있으며
                    반드시 이 직종 리스트 중에 선택해야 합니다.
                    형식은 다음과 같습니다:
                    {
                      "workFieldList": [
                        {
                          "workNumber": 1,
                          "workFieldName": "🎦직종",
                          "workFieldDescription": "직종에 대한 설명",
                          "workFieldReason": "이 직종이 추천된 이유"
                        },
                        ...
                      ]
                    }
                    추천된 직종은 사용자의 경력과 학력, 일에서 느끼는 보람을 고려해야 합니다.
                    직종 이름은 간결하고 명확해야 하며, 설명은 이해하기 쉽게 작성해야 합니다.
                    JSON 외의 다른 응답은 금지됩니다.
                """;

        return openAIService.getChatGPTResponse(systemMessage, userMessage);
    }


    @Transactional
    public List<FinalJobSelectResponse> getFinalJobSelectResponse(FinalJobSelectRequest request) throws JsonProcessingException {

        String json = objectMapper.writeValueAsString(request);
        log.info("json = {}", json);
        String systemMessage = """
                당신은 AI 기반 직종 추천 시스템입니다.
                사용자의 회사명, 경력, 자격증, 직업에서 느끼는 보람, 시군구, 통근시간, 근무시간, 원하는 업무분야 등의 정보를 기반으로 적절한 직종 3개와,
                그 직종의 현재 채용중인 채용링크 3개를 추천해야 합니다.
                형식은 다음과 같습니다:
                {
                  [
                    {
                      "rank" : 1,
                      "recommend_job_name": "직업 이름",
                      "recommend_company_url1":  "실제로 클릭 가능한 직업 채용 URL1",
                      "recommend_job_url1":  "recommend_company_url1의 실제 회사 이름",
                      "recommend_company_url2":  "실제로 클릭 가능한 직업 채용 URL2",
                      "recommend_job_url2":  "recommend_company_url2의 실제 회사 이름",
                      "recommend_company_url3":  "실제로 클릭 가능한 직업 채용 URL3"
                      "recommend_job_url3":  "recommend_company_url3의 실제 회사 이름",
                    },
                    ...
                  ]
               }
               """;

        String response = openAIService.getChatGPTResponse(systemMessage, json);

        log.info("response = {}", response);
        return objectMapper.readValue(response, objectMapper.getTypeFactory().constructCollectionType(List.class, FinalJobSelectResponse.class));

    }
}