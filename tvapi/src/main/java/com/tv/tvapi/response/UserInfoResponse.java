package com.tv.tvapi.response;

import lombok.Data;

@Data
public class UserInfoResponse {

    private Long id;

    private String username;

    private String fullName;

    private String avt;

    private String email;
}
