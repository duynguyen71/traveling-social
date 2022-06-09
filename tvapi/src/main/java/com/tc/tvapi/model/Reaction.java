package com.tc.tvapi.model;

import com.tc.tvapi.enumm.EReactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reaction")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Reaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    private EReactionType type;

    @OneToMany(mappedBy = "reaction",fetch = FetchType.LAZY)
    private List<PostReaction> posts = new ArrayList<>();


}
