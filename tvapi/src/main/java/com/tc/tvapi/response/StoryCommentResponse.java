package com.tc.tvapi.response;

import com.tc.tvapi.enumm.EStoryCommentType;
import lombok.Data;

import java.util.Date;

@Data
public class StoryCommentResponse {

    private Long id;

    private String content;

    private EStoryCommentType type;

    private Date createDate;
}
