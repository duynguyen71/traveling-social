package com.tv.tvapi.helper;

import com.tv.tvapi.model.Comment;
import com.tv.tvapi.model.ParentChildComment;
import com.tv.tvapi.model.Post;
import com.tv.tvapi.model.User;
import com.tv.tvapi.request.BaseParamRequest;
import com.tv.tvapi.request.PostCommentRequest;
import com.tv.tvapi.response.BaseResponse;
import com.tv.tvapi.response.CommentResponse;
import com.tv.tvapi.service.CommentService;
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
    private final CommentService commentService;
    private final ModelMapper modelMapper;

    public ResponseEntity<?> getPostComments(Long postId, Map<String, String> params) {
        BaseParamRequest paramRequest = new BaseParamRequest(params);
        Pageable pageable = paramRequest.toPageRequest();
        Post post = postService.getById(postId);
        if (post != null) {
            List<Comment> comments = commentService.getComments(post, pageable);
            List<CommentResponse> commentResps = comments.stream()
                    .map(comment -> {
                        CommentResponse commentResp = modelMapper.map(comment, CommentResponse.class);
                        return commentResp;
                    }).collect(Collectors.toList());
            return BaseResponse.success(commentResps, "Get post comments success!");
        }
        return null;

    }

    public ResponseEntity<?> commentPost(Long id, PostCommentRequest request) {
        Post post = postService.getById(id);
        if (post != null) {
            Long attachmentId = request.getAttachmentId();
            String content = request.getContent();
            if (attachmentId == null && content == null)
                return BaseResponse.badRequest("Comment content is empty");
            Long commentReqId = request.getId();
            Comment comment;
            User currentUser = userService.getCurrentUser();
            if (commentReqId != null
                    && (comment = commentService.getByIdAndUser(commentReqId, currentUser)) != null) {
                //update comment
            } else {
                comment = new Comment();
                comment.setPost(post);
                comment.setUser(currentUser);
            }
            comment.setContent(content);
            comment.setStatus(comment.getStatus());
            comment = commentService.save(comment);

            //
            Long parentCommentId = request.getParentCommentId();
            if (parentCommentId != null) {
                Comment parentComment = commentService.getById(parentCommentId);
                if (parentComment != null) {
                    ParentChildComment parentChildComment = new ParentChildComment();
                    parentChildComment.setParentComment(parentComment);
                    parentChildComment.setChildComment(comment);
                    commentService.save(parentChildComment);
                }
            }
            //attachment
            if (attachmentId != null) {
                //TODO: save attachment
            }

            return BaseResponse.success(modelMapper.map(comment, CommentResponse.class), "Post comment success");

        }
        return BaseResponse.badRequest("Can not find post with id: " + id);
    }

    public ResponseEntity<?> getComment(Long commentId, Map<String, String> params) {

        return null;
    }
}
