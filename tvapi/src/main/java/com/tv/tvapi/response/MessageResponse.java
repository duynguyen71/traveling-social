package com.tv.tvapi.response;

import com.tv.tvapi.enumm.EMessageType;
import com.tv.tvapi.model.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Setter
@Getter
public class MessageResponse implements Serializable {

    private Long id;

    private String message;

    @Enumerated(EnumType.ORDINAL)
    private EMessageType type;

    private Integer status;

    private BaseUserResponse user;

    private Date createDate;

    private MessageResponse replyMessage;
}
