package com.tv.tvapi.service;

import com.tv.tvapi.model.StoryComment;
import com.tv.tvapi.repository.StoryCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoryCommentService {

    private final StoryCommentRepository repo;


    public StoryComment getById(Long commentId) {
        return repo.findById(commentId).orElse(null);
    }

    public StoryComment save(StoryComment storyComment) {
        return repo.saveAndFlush(storyComment);
    }
}
