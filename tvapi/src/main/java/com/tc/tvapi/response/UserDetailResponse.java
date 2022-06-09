package com.tc.tvapi.response;

import lombok.Data;

import java.util.Date;

@Data
public class UserDetailResponse {

    private Long id;

    private String username;

    private String fullName;

    private String avt;

    private String email;

    private int followerCounts;

    private int followingCounts;

    private String bio;

    private String background;

    private Date createDate;


}
