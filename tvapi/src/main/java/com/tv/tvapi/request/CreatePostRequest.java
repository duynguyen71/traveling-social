package com.tv.tvapi.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;

@Data
public class CreatePostRequest {

    private Long id;

    @NotNull
    private String caption;

    private Integer status = 1;

    private Integer active = 1;


    private List<ContentUploadRequest> contents = new LinkedList<>();


}
