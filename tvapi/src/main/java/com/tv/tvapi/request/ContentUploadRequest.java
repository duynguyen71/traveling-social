package com.tv.tvapi.request;

import lombok.Data;

@Data
public class ContentUploadRequest {

    private Long id;

    private Integer pos;

    private String caption;

    private Long attachmentId;

    private Integer status = 1;

    private Integer active = 1;
}
