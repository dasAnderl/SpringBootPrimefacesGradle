package com.anderl.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

/**
 * Created by dasanderl on 12.09.14.
 */
@Service
public class UserService {

    public String getUserNameFromService() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUsername();
    }

}
