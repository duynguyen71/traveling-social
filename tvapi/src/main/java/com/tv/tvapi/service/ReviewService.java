package com.tv.tvapi.service;

import com.tv.tvapi.model.Review;
import com.tv.tvapi.model.User;
import com.tv.tvapi.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository repo;

    public Review saveReview(Review review) {
        return repo.saveAndFlush(review);
    }

    public List<Review> getReviewPosts(Integer status, Pageable pageable) {
        return repo.findReviewPostsNative(status, pageable);
    }


    public Review getByIdAndUser(Long reqId, User user) {
        return repo.findByIdAndUser(reqId, user).orElse(null);
    }

    public Review getByIdAndStatus(Long reviewId, int status) {
        return repo.findByIdAndStatus(reviewId, status).orElse(null);
    }

    public List<Review> getUserReviewPosts(User user,Integer status, Pageable pageRequest) {
        return repo.findByUserAndStatus(user,status, pageRequest);
    }
}
