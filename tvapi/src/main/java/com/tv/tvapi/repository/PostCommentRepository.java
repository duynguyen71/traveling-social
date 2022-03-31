package com.tv.tvapi.repository;

import com.tv.tvapi.model.Post;
import com.tv.tvapi.model.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, Long> {

    List<PostComment> findByPost(Post post);
}
