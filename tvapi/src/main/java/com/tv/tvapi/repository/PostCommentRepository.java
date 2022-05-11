package com.tv.tvapi.repository;

import com.tv.tvapi.model.PostComment;
import com.tv.tvapi.model.Post;
import com.tv.tvapi.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, Long> {

    Optional<PostComment> findByIdAndUser(Long id, User user);

    List<PostComment> findByPost(Post post);

//    List<PostComment> findByPostAndStatus(Post post, Integer status, Pageable pageable);

//    List<PostComment> findByPostAndStatusAndParentIsNull(Post post, Integer status);

    List<PostComment> findByPostAndParentIsNull(Post post, Pageable pageable);

}
