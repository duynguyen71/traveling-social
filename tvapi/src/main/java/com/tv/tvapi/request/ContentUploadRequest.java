package com.tv.tvapi.request;

import lombok.Data;

@Data
public class ContentUploadRequest {

    private Long id;

    private Integer pos;

    private String caption;

    private Long fileId;
}
