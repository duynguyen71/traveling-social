package com.tv.tvapi.controller;

import com.tv.tvapi.helper.UserHelper;
import com.tv.tvapi.request.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/public")
@RequiredArgsConstructor
public class PublicController {

    private final UserHelper userHelper;

    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {
        return userHelper.getUsers();
    }

    @GetMapping("validation-input")
    public ResponseEntity<?> validationInput(@RequestParam("input") String input,
                                             @RequestParam("value") String value) {
        return userHelper.validationInput(input, value);
    }

    @PostMapping("/users/registration")
    public ResponseEntity<?> registrationAccount(@RequestBody @Valid RegistrationRequest registrationRequest) {
        return userHelper.registrationAccount(registrationRequest);
    }

    @GetMapping("/images/{name}")
    public ResponseEntity<?> getImage(@PathVariable("name") String name) {
        return userHelper.getImage(name);
    }
}
