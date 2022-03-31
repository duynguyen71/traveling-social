package com.tv.tvapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.*;

@Table(name = "file_upload")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileUpload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "original_name")
    private String originalName;

    @Column(name = "content_type")
    private String contentType;

    private Long size;

    private String ext;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false, name = "upload_date")
    private Date uploadDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_date")
    private Date updateDate;

    private Integer active;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "upload_by")
    private User uploadBy;

    //
    @OneToMany(mappedBy = "cover", fetch = FetchType.LAZY)
    private Set<Story> stories = new HashSet<>();


    @OneToMany(mappedBy = "file", fetch = FetchType.LAZY)
    private List<PostContent> postContents = new ArrayList<>();

    @OneToMany(mappedBy = "attachment", fetch = FetchType.LAZY)
    private List<PostComment> postComments = new ArrayList<>();


}
