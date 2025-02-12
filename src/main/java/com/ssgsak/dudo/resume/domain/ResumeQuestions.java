package com.ssgsak.dudo.resume.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

// 이력서들
@ToString(of = {"id", "resume_title", "resume_published"})
@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class ResumeQuestions {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String resume_title;
//    private String resume_body;

    @LastModifiedDate
    private LocalDate resume_published;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "resumeQuestions")
    private List<ResumeMainInfo> resumeMainInfoList = new ArrayList<>();


    public void addMainInfo(ResumeMainInfo resumeMainInfo) {
        resumeMainInfoList.add(resumeMainInfo);
        resumeMainInfo.changeResumeQuestions(this);

    }

    public ResumeQuestions(String resume_title) {
        this.resume_title = resume_title;
    }


}
