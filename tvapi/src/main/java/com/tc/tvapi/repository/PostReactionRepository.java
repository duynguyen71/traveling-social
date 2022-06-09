package com.tc.tvapi.repository;

import com.tc.tvapi.model.Post;
import com.tc.tvapi.model.User;
import com.tc.tvapi.model.PostReaction;
import com.tc.tvapi.model.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostReactionRepository extends JpaRepository<PostReaction, Long> {

    boolean existsByUser(User user);

    boolean existsByUserAndReaction(User user,Reaction reaction);

    Optional<PostReaction> findByUser(User user);

    Long countByPostAndStatus(Post post, Integer status);

    List<PostReaction> getByPostAndStatus(Post post, Integer status);

    Optional<PostReaction> findByUserAndPostAndStatus(User user,Post post,Integer status);
}
