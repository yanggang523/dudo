package com.ssgsak.dudo.workRecommend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssgsak.dudo.config.OpenAIService;
import com.ssgsak.dudo.workRecommend.request.FinalJobSelectRequest;
import com.ssgsak.dudo.workRecommend.request.WorkFieldRequestForAi;
import com.ssgsak.dudo.workRecommend.response.FinalJobSelectResponse;
import com.ssgsak.dudo.workRecommend.response.WorkFieldListResponse;
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
    public List<WorkFieldListResponse> getWorkFieldWithAi(WorkFieldRequestForAi request) throws JsonProcessingException {

        String userMessage = objectMapper.writeValueAsString(request);
        String systemMessage = """
                     당신은 AI 기반 관심분야 추천 시스템입니다.
                    사용자의 회사명, 경력, 자격증, 직업에서 느끼는 보람과 즐거움 정보를 기반으로 적절한 관심분야 6개를 추천해야 합니다.
                    직종 종류에는
                    [1 건설
                2 전기/전자
                3 음식서비스
                4 기계
                5 안전관리
                6 경영, 회계, 사무
                7 경비, 사지
                8 정보통신
                9 사회복지, 종교
                10 문화예술, 디자인, 방송
                11 식품, 가공
                12 이용, 숙박, 여행, 오락, 스포츠
                13 화학
                14 제조
                15 농림어업
                16 보건, 의료
                17 의복, 패션, 가구, 직물
                18 운송
                19 영업, 판매
                20 운전, 운송
                21 전문, 운영]이 있으며
                    반드시 이 직종 리스트 중에 선택해야 합니다.
                    형식은 다음과 같습니다:
                
                       [
                        {
                          "workNumber": 1,
                          "workFieldName": "🎦직종",
                          "workFieldDescription": "직종에 대한 설명",
                          "workFieldReason": "이 직종이 추천된 이유"
                        },
                        ...
                      ]
                
                    추천된 직종은 사용자의 경력과 학력, 일에서 느끼는 보람을 고려해야 합니다.
                    직종 이름은 간결하고 명확해야 하며, 설명은 이해하기 쉽게 작성해야 합니다.
                    JSON 외의 다른 응답은 금지됩니다.
                """;

        String json = openAIService.getChatGPTResponse(systemMessage, userMessage);
        return objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, WorkFieldListResponse.class));
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