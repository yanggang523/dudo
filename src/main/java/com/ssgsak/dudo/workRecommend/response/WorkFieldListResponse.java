package com.ssgsak.dudo.workRecommend.response;

import lombok.Builder;
import lombok.Data;

@Data
public class WorkFieldListResponse {

    private int workNumber;
    private String workFieldName;
    private String workFieldDescription;
    private String workFieldReason;


    @Builder
    public WorkFieldListResponse(int workNumber, String workFieldName, String workFieldDescription, String workFieldReason) {
        this.workNumber = workNumber;
        this.workFieldName = workFieldName;
        this.workFieldDescription = workFieldDescription;
        this.workFieldReason = workFieldReason;
    }
}
