package com.tv.tvapi.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
public class ChatGroupResponse {

    private Long id;

    private String name;

    private Set<BaseUserResponse> users = new LinkedHashSet<>();

    private MessageResponse lastMessage;

    private Date createDate;

    private Date updateDate;
}
