package com.tv.tvapi.repository;

import com.tv.tvapi.model.Comment;
import com.tv.tvapi.model.ParentChildComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParentChildCommentRepository extends JpaRepository<ParentChildComment, Long> {

    List<ParentChildComment> findAllByParentComment(Comment comment);
}
