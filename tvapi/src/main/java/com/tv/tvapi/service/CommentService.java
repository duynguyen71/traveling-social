package com.tv.tvapi.service;

import com.tv.tvapi.model.Comment;
import com.tv.tvapi.model.ParentChildComment;
import com.tv.tvapi.model.Post;
import com.tv.tvapi.model.User;
import com.tv.tvapi.repository.CommentRepository;
import com.tv.tvapi.repository.ParentChildCommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentRepository commentRepo;
    private final ParentChildCommentRepository parentChildCommentRepo;

    public Comment getById(Long id) {
        return commentRepo.findById(id).orElse(null);
    }

    public Comment getByIdAndUser(Long id, User user) {
        return commentRepo.findByIdAndUser(id, user).orElse(null);
    }

    public Comment save(Comment comment) {
        return commentRepo.saveAndFlush(comment);
    }

    public ParentChildComment save(ParentChildComment comment) {
        return parentChildCommentRepo.saveAndFlush(comment);
    }

    public List<Comment> getComments(Post post, Pageable pageable) {
        return commentRepo.getPostCommentsNative(post.getId(), pageable);
    }

    public List<Comment> getAnswers(Comment parent) {
        return parentChildCommentRepo.findAllByParentComment(parent).stream()
                .map(parentChildComment -> parentChildComment.getChildComment()).collect(Collectors.toList());
    }
}
