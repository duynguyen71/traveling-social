package com.tv.tvapi.repository;

import com.tv.tvapi.model.StoryComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryCommentRepository extends JpaRepository<StoryComment, Long> {
}
