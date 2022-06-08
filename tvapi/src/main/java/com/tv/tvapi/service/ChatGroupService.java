package com.tv.tvapi.service;

import com.tv.tvapi.model.ChatGroup;
import com.tv.tvapi.model.ChatGroupUser;
import com.tv.tvapi.model.User;
import com.tv.tvapi.repository.ChatGroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatGroupService {

    private final ChatGroupRepository repo;

    public List<ChatGroup> getGroups(User user, Integer status){
        List<ChatGroup> groups = repo.findByUsers_UserAndStatus(user, status);
        return groups;
    }

    public ChatGroup  getGroup(Long id,User user,Integer status){
        return repo.findByIdAndUsers_UserAndStatus(id,user,1).orElse(null);
    }

    public ChatGroup getGroupByUsers(Collection<ChatGroupUser>users,Integer status){
        return repo.findByUsersInAndStatus(users,status).orElse(null);
    }

    public ChatGroup getGroupByTwoUsers(Long id1,Long id2){
        return repo.findByTwoUser(id1,id2).orElse(null);
    }

    public ChatGroup saveFlush(ChatGroup chatGroup) {
        return repo.save(chatGroup);
    }
}
