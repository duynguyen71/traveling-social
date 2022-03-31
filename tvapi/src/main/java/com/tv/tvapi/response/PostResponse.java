package com.tv.tvapi.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class PostResponse {

    private Long id;

    private String caption;

    private Integer status;

    private Integer active;

    private UserInfoResponse uploadBy;

    private List<PostContentResponse> contents = new ArrayList<>();

    private Date createDate;

    private Date updateDate;

    private Integer likeCounts;

}
