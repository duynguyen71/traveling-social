package com.tc.tvapi.controller;

import com.tc.tvapi.helper.UserHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {


    private final UserHelper userHelper;

    @GetMapping("/verification")
    public ResponseEntity<?> verificationAccount(@RequestHeader("code") String code) {
        return userHelper.verificationAccount(code);
    }
}
