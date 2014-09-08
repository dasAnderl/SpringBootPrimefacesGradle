package com.anderl.hibernate.ext;

import org.hibernate.Session;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * Created by ga2unte on 1/29/14.
 */
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "session")
@Component
public class HibernateSessionCache {

    private org.slf4j.Logger log = LoggerFactory.getLogger(HibernateSessionCache.class);

    private Session session;

    @PreDestroy
    public void destroy() {
        SessionFactoryUtils.closeSession(getSession());
        log.debug("Closed hibernate session");
        this.session = null;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        log.debug("Setting session {}. old session was {}", session, this.session);
        SessionFactoryUtils.closeSession(getSession());
        this.session = session;
    }
}
