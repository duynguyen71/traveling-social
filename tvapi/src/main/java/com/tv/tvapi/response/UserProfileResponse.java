package com.tv.tvapi.response;

import lombok.Data;

import java.util.Date;

@Data
public class UserProfileResponse {


    private Long id;

    private String username;

    private String fullName;

    private String avt;

    private int followerCounts;

    private int followingCounts;

    private String bio;

    private String background;

    private Date createDate;

    private Boolean isFollowed = false;
}
