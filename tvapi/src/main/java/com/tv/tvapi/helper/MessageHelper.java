package com.tv.tvapi.helper;

import com.tv.tvapi.model.ChatGroup;
import com.tv.tvapi.model.Message;
import com.tv.tvapi.model.User;
import com.tv.tvapi.request.BaseParamRequest;
import com.tv.tvapi.request.MessageRequest;
import com.tv.tvapi.response.BaseResponse;
import com.tv.tvapi.response.MessageResponse;
import com.tv.tvapi.service.ChatGroupService;
import com.tv.tvapi.service.ChatGroupUserService;
import com.tv.tvapi.service.MessageService;
import com.tv.tvapi.service.UserService;
import com.tv.tvapi.utilities.ValidationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
@Slf4j
public class MessageHelper {

    private final MessageService messageService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final ChatGroupUserService chatGroupUserService;
    private final ChatGroupService chatGroupService;

    public ResponseEntity<?> getMessages(Long groupId, Map<String, String> param) {
        User currentUser = userService.getCurrentUser();
        ChatGroup chatGroup = chatGroupService.getGroup(groupId, currentUser, 1);
        if (chatGroup == null) {
            return BaseResponse.badRequest("can not find chat group with id : " + groupId);
        }
        Pageable pageable = new BaseParamRequest(param).toPageRequest();
        List<Message> messages = messageService.getMessages(chatGroup, 1, pageable);
        List<MessageResponse> messageResponses = messages.stream()
                .map(m -> modelMapper.map(m, MessageResponse.class))
                .collect(Collectors.toList());

        return BaseResponse.success(messageResponses, "get message in group " + groupId + " success");
    }

    public ResponseEntity<?> saveMessage(Long chatGroupId, MessageRequest messageRequest) {
        String messageText = messageRequest.getMessage();
        List<Long> attachments = messageRequest.getAttachments();
        if (ValidationUtil.isNullOrBlank(messageText) && attachments.isEmpty()) {
            return BaseResponse.badRequest("message is not valid");
        }
        User currentUser = userService.getCurrentUser();
        ChatGroup chatGroup = chatGroupService.getGroup(chatGroupId, currentUser, 1);
        if (chatGroup == null) {

            return BaseResponse.badRequest("can not find chat group with id : " + chatGroupId);
        }
        Message message = new Message();
        message.setMessage(messageText);
        message.setUser(currentUser);
        message.setStatus(1);
        message.setChatGroup(chatGroup);

        Long replyMessageId = messageRequest.getReplyMessageId();
        Message replyMessage = null;
        if ((replyMessage = messageService.getMessageInGroup(replyMessageId, 1, chatGroup)) != null) {
            message.setReplyMessage(replyMessage);
        }
        message = messageService.saveFlush(message);
        MessageResponse messageResponse = modelMapper.map(message, MessageResponse.class);
        return BaseResponse.success(messageResponse, "save message success");
    }


    public ResponseEntity<?> removeMessage(Long messageId){
        Message message = messageService.getMessage(messageId, 1,userService.getCurrentUser());
        if(message==null){
            return BaseResponse.badRequest("message is not exist");
        }
        message.setStatus(0);
        messageService.save(message);
        return BaseResponse.success("remove message success");
    }
}
