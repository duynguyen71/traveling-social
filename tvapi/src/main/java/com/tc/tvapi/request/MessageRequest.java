package com.tc.tvapi.request;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class MessageRequest {

    private String message;

    private Long replyMessageId;

    private List<Long> attachments = new LinkedList<>();


}
