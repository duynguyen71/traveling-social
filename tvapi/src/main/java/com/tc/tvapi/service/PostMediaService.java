package com.tc.tvapi.service;

import com.tc.tvapi.repository.PostContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostMediaService {

    private final PostContentRepository postContentRepository;


}
