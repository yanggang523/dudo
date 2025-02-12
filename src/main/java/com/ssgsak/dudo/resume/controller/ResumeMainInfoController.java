package com.ssgsak.dudo.resume.controller;

import com.ssgsak.dudo.resume.service.ResumeMainInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ResumeMainInfoController {


    @PostMapping("/api/resume/questionNameLocation")
    public ResponseEntity<String> getResumeNameLocationWithAi() {
        return null;
    }



}
