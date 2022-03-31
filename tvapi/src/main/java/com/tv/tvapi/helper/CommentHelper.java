package com.tv.tvapi.helper;

import com.tv.tvapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentHelper {

    private final UserService userService;

}
