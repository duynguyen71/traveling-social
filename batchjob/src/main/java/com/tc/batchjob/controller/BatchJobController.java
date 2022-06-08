package com.tc.batchjob.controller;

import com.tv.tvapi.feign.AuthenticateFeignClient;
import com.tv.tvapi.helper.UserHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class BatchJobController {


    private final UserHelper userHelper;
    private final AuthenticateFeignClient feignClient;

    @GetMapping("/hihi")
    public ResponseEntity<?> getUser() {
        return ResponseEntity.ok("BatchJOb");
    }

}
