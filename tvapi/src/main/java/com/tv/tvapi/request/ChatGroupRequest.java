package com.tv.tvapi.request;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
public class ChatGroupRequest {

    private String name;

    private Set<Long> memberIds = new LinkedHashSet<>();
}
