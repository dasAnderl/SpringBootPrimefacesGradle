package com.anderl.domain;

import com.anderl.dao.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by dasanderl on 17.09.14.
 */
@Component
public class AuditingAware implements AuditorAware<User> {

    private final static Logger log = LoggerFactory.getLogger(AuditingAware.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getCurrentAuditor() {
        //TODO fix me with proper logic in prod
        log.warn("here we dynamically create a user for audting. Fix in prod");
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext.getAuthentication() == null) {
            User randomUser = DomainProvider.getRandomUser();
            userRepository.save(randomUser);
            return randomUser;
        }
        String userName = securityContext.getAuthentication().getName();
        List<User> userByName = userRepository.findByName(userName);
        if (CollectionUtils.isEmpty(userByName)) {
            User user = new User();
            user.setName(userName);
            userRepository.save(user);
            return user;
        }
        return userByName.get(0);
    }
}
