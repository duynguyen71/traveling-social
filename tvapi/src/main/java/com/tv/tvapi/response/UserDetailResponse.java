package com.tv.tvapi.response;

import lombok.Data;

@Data
public class UserDetailResponse {

    private Long id;

    private String username;

    private String fullName;

    private String avt;

    private String email;

    private int followerCounts;

    private int followingCounts;
}
