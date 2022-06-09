package com.tv.tvapi.service;

import com.tv.tvapi.repository.PostContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostMediaService {

    private final PostContentRepository postContentRepository;


}
