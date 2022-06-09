package com.tv.tvapi.repository;

import com.tv.tvapi.model.ChatGroup;
import com.tv.tvapi.model.ChatGroupUser;
import com.tv.tvapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatGroupUserRepository extends JpaRepository<ChatGroupUser,Long> {


     List<ChatGroupUser> findByChatGroupAndStatus(ChatGroup chatGroup,Integer status);

     boolean existsByChatGroupAndUserAndStatus(ChatGroup group,User user,Integer status);


}
