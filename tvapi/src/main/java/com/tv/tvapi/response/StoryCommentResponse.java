package com.tv.tvapi.response;

import com.tv.tvapi.enumm.EStoryCommentType;
import lombok.Data;

import java.util.Date;

@Data
public class StoryCommentResponse {

    private Long id;

    private String content;

    private EStoryCommentType type;

    private Date createDate;
}
