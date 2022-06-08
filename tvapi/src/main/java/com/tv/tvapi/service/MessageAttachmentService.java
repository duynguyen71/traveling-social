package com.tv.tvapi.service;

import com.tv.tvapi.repository.MessageAttachmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageAttachmentService {

    private final MessageAttachmentRepository repo;
}
