package com.tv.tvapi.repository;

import com.tv.tvapi.model.Post;
import com.tv.tvapi.model.Comment;
import com.tv.tvapi.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPost(Post post);

    Optional<Comment> findByIdAndUser(Long id, User user);

    //    @Query(
//            nativeQuery = true,
//            value = "SELECT * FROM comment c JOIN parent_child_comment pc \n" +
//                    "ON c.id NOT LIKE pc.child_comment_id\n" +
//                    "JOIN post p ON p.id = c.post_id\n" +
//                    "WHERE p.id = :postId AND p.status = 1"
//    )
    @Query(
            nativeQuery = true,
            value = "SELECT * FROM comment c \n" +
                    "JOIN post p\n" +
                    "ON c.post_id = p.id\n" +
                    "WHERE p.id = :postId \n" +
                    "AND c.id NOT IN (SELECT child_comment_id FROM parent_child_comment)\n" +
                    "AND p.status = 1\n" +
                    "AND c.status = 1"
    )
    List<Comment> getPostCommentsNative(@Param("postId") Long postId, Pageable pageable);


}
