package com.tv.tvapi.request;

import com.tv.tvapi.enumm.EPostType;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

    @Enumerated(EnumType.ORDINAL)
    private EPostType type ;


    private List<ContentUploadRequest> contents = new LinkedList<>();


}
