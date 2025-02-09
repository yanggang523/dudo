package com.ssgsak.dudo.workRecommend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssgsak.dudo.workRecommend.config.OpenAIService;
import com.ssgsak.dudo.workRecommend.request.WorkFieldRequestForAi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorkQuestionOpenAiService {

    private final ObjectMapper objectMapper;
    private final OpenAIService openAIService;



    @Transactional
    public String getWorkField(WorkFieldRequestForAi request) throws JsonProcessingException {

        String userMessage = objectMapper.writeValueAsString(request);
        String systemMessage = """
                    당신은 AI 기반 직업 추천 시스템입니다.
                    사용자의 회사명, 경력, 학력, 직업에서 느끼는 보람과 즐거움 정보를 기반으로 적절한 직업 6개를 추천해야 합니다.
                    정확하고 논리적인 직업을 추천하며, JSON 형식으로만 응답해야 합니다.
                    형식은 다음과 같습니다:  
                    {
                      "workFieldList": [
                        {
                          "workNumber": 1,
                          "workFieldName": "직업명",
                          "workFieldDescription": "직업에 대한 설명",
                          "workFieldReason": "이 직업이 추천된 이유"
                        },
                        ...
                      ]
                    }
                    추천된 직업은 사용자의 경력과 학력, 일에서 느끼는 보람을 고려해야 합니다.
                    직업 이름은 간결하고 명확해야 하며, 설명은 이해하기 쉽게 작성해야 합니다.
                    JSON 외의 다른 응답은 금지됩니다.
                """;

        return openAIService.getChatGPTResponse(systemMessage, userMessage);
    }

}
