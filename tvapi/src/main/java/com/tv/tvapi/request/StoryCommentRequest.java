package com.tv.tvapi.request;

import com.tv.tvapi.enumm.EStoryCommentType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class StoryCommentRequest {

    private Long id;

    @NotBlank
    private String content;

    //1 public comment, 2 private comment
    @NotNull
    private Integer type = 1;


}
