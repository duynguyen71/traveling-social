package com.tc.batchjob.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/public")
public class HihiController {


    @GetMapping("/hihi")
    public ResponseEntity<?> gethihih() {
        return ResponseEntity.ok("hihi");
    }
}
