package com.tv.tvapi.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageAttachmentResponse {

    private Long id;

    private FileUploadResponse fileUpload;
}
