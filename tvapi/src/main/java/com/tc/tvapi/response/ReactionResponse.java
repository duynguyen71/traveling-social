package com.tc.tvapi.response;

import com.tc.tvapi.enumm.EReactionType;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class ReactionResponse {

    private Long id;

    @Enumerated(EnumType.ORDINAL)
    private EReactionType type;

    private String name;
}
