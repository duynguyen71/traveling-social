package com.tv.tvapi.controller;

import com.tv.tvapi.helper.*;
import com.tv.tvapi.request.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {

    private final UserHelper userHelper;
    private final PostHelper postHelper;
    private final FileUploadHelper fileUploadHelper;
    private final CommentHelper commentHelper;
    private final PostReactionHelper postReactionHelper;
    private final ReviewHelper reviewHelper;

    @GetMapping("/users/me/files/{name}")
    public ResponseEntity<?> findFile(@PathVariable("name") String fileName) {
        return userHelper.getImage(fileName);
    }

    @PostMapping("/users/me/files")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        return fileUploadHelper.uploadFile(file);
    }

    @GetMapping("/users/searching")
    public ResponseEntity<?> search(@RequestParam Map<String, String> params) {
        return userHelper.searchingUsers(params);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<?> getUserInfo(@PathVariable("userId") Long userId) {
        return userHelper.getUserProfile(userId);
    }

    @PutMapping("/users/me/avt")
    public ResponseEntity<?> uploadAvt(@RequestParam("file") MultipartFile file) {
        return userHelper.updateAvt(file);
    }

    @PutMapping("/users/me/background")
    public ResponseEntity<?> updateBackground(@RequestParam("file") MultipartFile file) {
        return userHelper.updateBackground(file);
    }

    @GetMapping("/users/me")
    public ResponseEntity<?> getCurrentUserInfo() {
        return userHelper.getCurrentUserDetail();
    }

    @PostMapping("/users/me")
    public ResponseEntity<?> updateCurrentUserInfo(@Valid @RequestBody UserUpdateRequest userUpdateRequest) {
        return userHelper.updateCurrentUserInfo(userUpdateRequest);
    }

    @GetMapping("/users/me/posts")
    public ResponseEntity<?> getCurrentUserPosts(@RequestParam Map<String, String> params) {
        return postHelper.getCurrentUserPosts(params);
    }

    @PutMapping("/posts/{postId}/status/{status}")
    public ResponseEntity<?> updatePostStatus(@PathVariable("postId") Long postId, @PathVariable("status") Integer status) {
        return postHelper.updateStatus(postId, status);
    }

    @GetMapping(value = "/posts"
//            ,
//            produces = "application/json;charset=UTF-8"
    )
    public ResponseEntity<?> getPosts(@RequestParam Map<String, String> params) {
        return postHelper.getPosts(params);
    }

    @GetMapping("/users/me/posts/{postId}/comments")
    public ResponseEntity<?> getPostParentComments(@PathVariable("postId") Long postId) {
        return postHelper.getPostComments(postId);
    }

    @PostMapping("/users/me/posts")
    public ResponseEntity<?> createPost(@RequestBody @Valid CreatePostRequest createPostRequest) {
        return postHelper.createPost(createPostRequest);
    }

    @GetMapping("/users/me/following")
    public ResponseEntity<?> getFollowingUsers(@RequestParam Map<String, String> param) {
        return userHelper.getFollowingUsers(param);
    }

    @GetMapping("/users/me/follow/{userId}")
    public ResponseEntity<?> followRequest(@PathVariable("userId") Long userId) {
        return userHelper.followUser(userId);
    }

    @GetMapping("/users/me/followers")
    public ResponseEntity<?> getFollowers(@RequestParam Map<String, String> param) {
        return userHelper.getFollowers(param);
    }

    @GetMapping("/users/me/unfollow/{id}")
    public ResponseEntity<?> unFollow(@PathVariable("id") Long id) {
        return userHelper.unFollowUser(id);
    }


    @GetMapping("/stories")
    public ResponseEntity<?> getFriendStories(@RequestParam Map<String, String> params) {
        return postHelper.getStories(params);
    }

    @GetMapping("/users/me/stories")
    public ResponseEntity<?> getCurrentUserStories(@RequestParam Map<String, String> params) {
        return postHelper.getStories(params);
    }


    @PostMapping("/posts/{id}/comments")
    public ResponseEntity<?> commentPost(@PathVariable("id") Long id, @RequestBody PostCommentRequest postCommentRequest) {
        return commentHelper.commentPost(id, postCommentRequest);
    }

    @GetMapping("/posts/{id}/comments")
    public ResponseEntity<?> getPostParentComments(@PathVariable("id") Long postId, @RequestParam Map<String, String> params) {
        return commentHelper.getRootComments(postId, params);
    }

    @PutMapping("/comments/{commentId}/status/{status}")
    public ResponseEntity<?> updateCommentStatus(@PathVariable("commentId") Long commentId, @PathVariable("status") Integer status) {
        return commentHelper.updateCommentStatus(commentId, status);
    }

    /**
     * get current user comments on post
     *
     * @param postId
     * @return
     */
    @GetMapping("/posts/{postId}/comments/me")
    public ResponseEntity<?> getCurrentUserPostComments(@PathVariable("postId") Long postId) {
        return commentHelper.getCurrentUserPostComments(postId);
    }

    @GetMapping("/comments/{commentId}/reply")
    public ResponseEntity<?> getReplyComments(@PathVariable("commentId") Long commentId, @RequestParam Map<String, String> params) {
        return commentHelper.getReplyComments(commentId, params);
    }

    /**
     * tha binh luan cho bai viet (post,story)
     *
     * @return
     */
    @PostMapping("/posts/reactions")
    public ResponseEntity<?> postReaction(@RequestBody @Valid ReactionRequest reactionRequest) {
        return postReactionHelper.savePostReaction(reactionRequest);
    }

    @GetMapping("/posts/{postId}/reactions")
    public ResponseEntity<?> getPostReactions(@PathVariable("postId") Long postId) {
        return postReactionHelper.getPostReactions(postId);
    }

    //get current user review posts
    @GetMapping("/users/me/reviews")
    public ResponseEntity<?> getCurrentUserReviewPosts(@RequestParam Map<String, String> param) {
        return reviewHelper.getCurrentUserReviewPosts(param);
    }

    @GetMapping("/users/{userId}/reviews")
    public ResponseEntity<?> getCurrentUserReviewPosts(@RequestParam Map<String, String> param, @PathVariable("userId") Long userId) {
        return reviewHelper.getUserReviewPosts(userId, param);
    }

    @GetMapping("/reviews")
    public ResponseEntity<?> getReviewPosts(@RequestParam Map<String, String> param) {
        return reviewHelper.getReviewPosts(param);
    }

    @PostMapping("/reviews")
    public ResponseEntity<?> saveReview(@RequestBody @Valid ReviewRequest request) {
        return reviewHelper.saveReview(request);
    }

    @GetMapping("/reviews/{reviewId}")
    public ResponseEntity<?> getReviewPostDetail(@PathVariable("reviewId") Long reviewId) {
        return reviewHelper.getReviewPostDetail(reviewId);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getTopActiveUsers(@RequestParam Map<String, String> param) {
        return userHelper.getTopUsers(param);
    }

    @GetMapping("/users/{userId}/posts")
    public ResponseEntity<?> getUserPosts(@PathVariable("userId") Long userId, @RequestParam Map<String, String> param) {
        return postHelper.getUserPosts(userId, param);
    }


}