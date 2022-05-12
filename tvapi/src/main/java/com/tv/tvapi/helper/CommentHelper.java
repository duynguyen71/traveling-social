package com.tv.tvapi.helper;

import com.tv.tvapi.model.FileUpload;
import com.tv.tvapi.model.PostComment;
import com.tv.tvapi.model.Post;
import com.tv.tvapi.model.User;
import com.tv.tvapi.request.BaseParamRequest;
import com.tv.tvapi.request.PostCommentRequest;
import com.tv.tvapi.response.BaseResponse;
import com.tv.tvapi.response.CommentResponse;
import com.tv.tvapi.service.FileStorageService;
import com.tv.tvapi.service.PostCommentService;
import com.tv.tvapi.service.PostService;
import com.tv.tvapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CommentHelper {

    private final UserService userService;
    private final PostService postService;
    private final PostCommentService postCommentService;
    private final ModelMapper modelMapper;
    private final FileStorageService fileStorageService;

    public ResponseEntity<?> getPostParentComments(Long postId, Map<String, String> params) {
        BaseParamRequest paramRequest = new BaseParamRequest(params);
        Pageable pageable = paramRequest.toPageRequest();
        List<PostComment> postComments = postCommentService.getPostParentComments(postId, 1, pageable);
        List<CommentResponse> rs = postComments.stream()
                .map(comment -> {
                    CommentResponse commentResp = modelMapper.map(comment, CommentResponse.class);
                    return commentResp;
                }).collect(Collectors.toList());
        return BaseResponse.success(rs, "Get post comments success!");

    }

    public ResponseEntity<?> commentPost(Long id, PostCommentRequest request) {
        Post post = postService.getById(id);
        if (post != null) {
            Long attachmentId = request.getAttachmentId();
            String content = request.getContent();
            if (attachmentId == null && content == null)
                return BaseResponse.badRequest("Comment content is empty");
            Long commentReqId = request.getId();
            PostComment postComment;
            User currentUser = userService.getCurrentUser();
            if (commentReqId != null
                    && (postComment = postCommentService.getByIdAndUser(commentReqId, currentUser)) != null) {
                //update comment
            } else {
                postComment = new PostComment();
                postComment.setPost(post);
                postComment.setUser(currentUser);
            }
            postComment.setContent(content);
            postComment.setStatus(1);
            //
            Long parentCommentId = request.getParentCommentId();
            PostComment parentComment = postCommentService.getById(parentCommentId);
            if (parentCommentId != null && parentComment != null)
                postComment.setParent(parentComment);
            //attachment
            if (attachmentId != null) {
                //TODO: save attachment
                FileUpload fileUpload = fileStorageService.getFileFromDb(attachmentId);
                if (fileUpload != null)
                    postComment.setAttachment(fileUpload);
            }
            postComment = postCommentService.save(postComment);
            return BaseResponse.success(modelMapper.map(postComment, CommentResponse.class), "Post comment success");

        }
        return BaseResponse.badRequest("Can not find post with id: " + id);
    }

    public ResponseEntity<?> getComment(Long commentId, Map<String, String> params) {

        return null;
    }

    public ResponseEntity<?> getCurrentUserPostComments(Long postId) {
        User currentUser = userService.getCurrentUser();
        List<PostComment> comments = postCommentService.getCurrentUserPostComments(postService.getById(postId), currentUser, 1);
        List<CommentResponse> rs = comments.stream()
                .map(c -> modelMapper.map(c, CommentResponse.class))
                .collect(Collectors.toList());
        return BaseResponse.success(rs, "get current user post comments success!");
    }

    public  ResponseEntity<?> updateCommentStatus(Long commentId,Integer status){
        User user = userService.getCurrentUser();
        PostComment comment = postCommentService.getByIdAndUser(commentId, user);
        if(comment!=null){
            comment.setStatus(status);
            postCommentService.save(comment);
            return BaseResponse.success("update comment status success");
        }
        return BaseResponse.badRequest("Could not find comment with id: "+commentId);
    }
}
