package com.tv.tvapi.repository;

import com.tv.tvapi.model.Post;
import com.tv.tvapi.model.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    int countByPost(Post post);

    List<PostLike> findByPost(Post post);
}