package com.tv.tvapi.service;

import com.tv.tvapi.model.*;
import com.tv.tvapi.repository.PostCommentRepository;
import com.tv.tvapi.repository.PostContentRepository;
import com.tv.tvapi.repository.PostLikeRepository;
import com.tv.tvapi.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostContentRepository postMediaFileRepo;
    private final PostLikeRepository postLikeRepo;
    private final PostCommentRepository postCommentRepo;

    public Post savePost(Post post) {
        return postRepository.saveAndFlush(post);
    }

    public PostContent savePostContent(PostContent postContent) {
        return postMediaFileRepo.save(postContent);
    }

    public PostContent getPostMediaFile(Long mediaFileId, Post post) {
        return postMediaFileRepo.findByIdAndPost(mediaFileId, post).orElse(null);
    }

    public List<PostContent> getPostContents(Post post) {
        return postMediaFileRepo.findByPostAndActiveOrderByPos(post, 1);
    }

    public Post getById(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    public List<Post> getUserPost(User user, Pageable pageable, int active, int status) {
        return postRepository.getUserPostNative(user.getId(), active, status, pageable);
    }

    public int countPostLike(Post post) {
        return postLikeRepo.countByPost(post);
    }

    public List<PostLike> getPostLikes(Post post) {
        return postLikeRepo.findByPost(post);
    }


    public List<PostComment> getPostComments(Post post) {
        return postCommentRepo.findByPost(post);
    }
}
