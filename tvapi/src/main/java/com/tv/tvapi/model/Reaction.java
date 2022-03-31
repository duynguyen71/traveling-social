package com.tv.tvapi.model;

import com.tv.tvapi.enumm.EReaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "reaction")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Reaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EReaction type;
}
