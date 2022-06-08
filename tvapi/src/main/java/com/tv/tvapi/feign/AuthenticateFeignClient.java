package com.tv.tvapi.feign;

import com.tv.tvapi.response.UserDetailResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "authenticateFeignClient")
public interface AuthenticateFeignClient {

    @GetMapping("/api/v1/member/me")
    UserDetailResponse getCurrentUserInfo();

}
