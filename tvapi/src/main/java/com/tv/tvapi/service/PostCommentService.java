package com.tv.tvapi.service;

import com.tv.tvapi.model.PostComment;
import com.tv.tvapi.model.Post;
import com.tv.tvapi.model.User;
import com.tv.tvapi.repository.PostCommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostCommentService {

    private final PostCommentRepository commentRepo;

    public PostComment getByIdAndUser(Long id, User user) {
        return commentRepo.findByIdAndUser(id, user).orElse(null);
    }

    public PostComment save(PostComment postComment) {
        return commentRepo.saveAndFlush(postComment);
    }

    /**
     * get post parent comments
     *
     * @param post
     * @param pageable
     * @return
     */
    public List<PostComment> getPostComments(Post post, Integer status, Pageable pageable) {
//        return commentRepo.findByPostAndStatus(post,status,pageable);
        return commentRepo.findByPostAndParentIsNull(post, pageable);
    }


}
