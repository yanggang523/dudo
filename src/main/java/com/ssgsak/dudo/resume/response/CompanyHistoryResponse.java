package com.ssgsak.dudo.resume.response;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class CompanyHistoryResponse {
    private String resume_company; // 회사명
    private int resume_service_year; // 근무년수
    private String resume_job_responsibilities; // 담당업무

}
