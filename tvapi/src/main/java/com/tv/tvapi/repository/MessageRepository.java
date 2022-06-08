package com.tv.tvapi.repository;

import com.tv.tvapi.model.ChatGroup;
import com.tv.tvapi.model.Message;
import com.tv.tvapi.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    Optional<Message> findFirstByChatGroupAndStatusOrderByCreateDateDesc(ChatGroup c,Integer status);

    List<Message> findByChatGroupAndStatus(ChatGroup chatGroup, Integer status, Pageable pageable);

    Optional<Message>findByIdAndStatusAndChatGroup(Long id,Integer status,ChatGroup chatGroup);

    Optional<Message> findByIdAndStatusAndUser(Long id, Integer status, User user);

}
