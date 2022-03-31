package com.tv.tvapi.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "post")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String caption;

    @OneToMany(mappedBy = "post",fetch = FetchType.LAZY)
    @OrderBy("pos ASC")
    private List<PostContent> contents = new LinkedList<>();

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", updatable = false)
    private Date createDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_date")
    private Date updateDate;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;

    private Integer active;

    private Integer status;

    @OneToMany(mappedBy = "post",fetch = FetchType.LAZY)
    private List<PostLike> postLikes = new ArrayList<>();
}
