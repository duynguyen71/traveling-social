package com.tv.tvapi.response;

import lombok.Data;

import java.util.Date;

@Data
public class FileUploadResponse {

    private Long id;

    private String name;

    private String contentType;

    private Date uploadDate;


}
