package com.tc.tvapi.repository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("AuthenticateFacde")
public class AuthenticateFacade {

    public Object getCurrentUserLogged() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
