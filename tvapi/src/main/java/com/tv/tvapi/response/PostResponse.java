package com.tv.tvapi.response;

import lombok.Data;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class PostResponse {

    private Long id;

    private String caption;

    private Integer status;

    private Integer active;

    private UserInfoResponse user;

    private List<PostContentResponse> contents = new ArrayList<>();

    private Long reactionCount;

    private Date createDate;

    private Date updateDate;

    private Integer likeCounts;

    private ReactionResponse myReaction;

}
