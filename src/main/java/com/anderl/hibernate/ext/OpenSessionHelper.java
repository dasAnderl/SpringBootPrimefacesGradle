package com.anderl.hibernate.ext;

import com.commerzbank.aop.utils.SpringUtils;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.async.CallableProcessingInterceptorAdapter;
import org.springframework.web.context.request.async.WebAsyncManager;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.Callable;

/**
 * Created by ga2unte on 1/29/14.
 */
public class OpenSessionHelper {

    public static Logger log = LoggerFactory.getLogger(OpenSessionHelper.class);

    private static final String REQ_KEY_PARTICIPATE = "PARTICIPATE";
    private static final String KEY_END_CONVERSATION = "KEY_END_CONVERSATION";

    public static boolean isEndConversationFlagSet(HttpServletRequest request) {
        Object endConversationParamValue = request.getSession().getAttribute(KEY_END_CONVERSATION);
        if (endConversationParamValue != null && (Boolean) endConversationParamValue) {
            return true;
        }
        return false;
    }

    public static void enableEndConversationFlag(HttpServletRequest request) {
        request.getSession().setAttribute(KEY_END_CONVERSATION, true);
    }

    public static void disableEndConversationFlag(HttpServletRequest request) {
        request.getSession().setAttribute(KEY_END_CONVERSATION, false);
    }

    public static void extendSessionToNextRequest(HttpServletRequest request) {
        request.setAttribute(REQ_KEY_PARTICIPATE, true);
    }

    public static boolean isExtendedToNextRequest(HttpServletRequest request) {
        return request.getAttribute(REQ_KEY_PARTICIPATE) != null;
    }

    protected static Session getSession(HttpServletRequest request, String alreadyFilteredAttributeName, SessionFactory sessionFactory) throws DataAccessResourceFailureException {
        Session session = getSessionFromCache();
        if (session != null && session.isOpen()) {
            log.debug("Using session from SessionHolder");
            return session;
        }
        try {
            session = sessionFactory.getCurrentSession();
            log.debug("Using session from sessionFactory.getCurrentSession()");
        } catch (HibernateException ex) {
        }
        try {
            if (session == null || !session.isOpen()) {
                session = sessionFactory.openSession();
                log.debug("Creating new session: sessionFactory.openSession()");
            }
            session.setFlushMode(FlushMode.MANUAL);
            setSessionToCache(session);
            return session;
        } catch (HibernateException ex) {
            throw new DataAccessResourceFailureException("Could not open Hibernate Session", ex);
        }
    }

    public static void endConversation(SessionFactory sessionFactory, HttpServletRequest request) {
        log.debug("End of conversation flag found. Closing and unbinding Session");
        unbindSession(sessionFactory);
        closeSessionFromCache();
        disableEndConversationFlag(request);
    }

    public static void unbindSession(SessionFactory sessionFactory) {
        try {
            TransactionSynchronizationManager.unbindResource(sessionFactory);
        } catch (Exception e) {
        }
    }

    public static void bindSessionToTransactionSnyManager(SessionFactory sessionFactory, WebAsyncManager asyncManager, String key, Session session) {
        SessionHolder sessionHolder = new SessionHolder(session);
        try {
            TransactionSynchronizationManager.bindResource(sessionFactory, sessionHolder);
        } catch (Exception e) {
            Session oldSession = getSessionFromHolder(sessionFactory);
            if (oldSession == null || oldSession.hashCode() != session.hashCode()) {
                log.warn("TransactionSynchronizationManager was already holding a different session. Destroying/unbinding old session");
                SessionFactoryUtils.closeSession(oldSession);
                unbindSession(sessionFactory);
                TransactionSynchronizationManager.bindResource(sessionFactory, sessionHolder);
            } else {
                log.warn("TransactionSynchronizationManager was already holding the session. Continuing.");
            }
        }

        asyncManager.registerCallableInterceptor(key,
                new SessionBindingCallableInterceptor(sessionFactory, sessionHolder));
    }

    private static Session getSessionFromHolder(SessionFactory sessionFactory) {
        return ((SessionHolder) TransactionSynchronizationManager.getResource(sessionFactory)).getSession();
    }

    public static Session getSessionFromCache() {
        return SpringUtils.getSpringBeanFromWebCtx(HibernateSessionCache.class).getSession();
    }

    public static void setSessionToCache(Session session) {
        SpringUtils.getSpringBeanFromWebCtx(HibernateSessionCache.class).setSession(session);
    }

    public static void closeSessionFromCache() {
        try {
            SpringUtils.getSpringBeanFromWebCtx(HibernateSessionCache.class).destroy();
        } catch (Exception e) {
            log.warn("Could not get SessionHolder");
        }
    }

    public static boolean applySessionBindingInterceptor(WebAsyncManager asyncManager, String key) {
        if (asyncManager.getCallableInterceptor(key) == null) {
            return false;
        }
        ((SessionBindingCallableInterceptor) asyncManager.getCallableInterceptor(key)).initializeThread();
        return true;
    }

    private static class SessionBindingCallableInterceptor extends CallableProcessingInterceptorAdapter {

        private final SessionFactory sessionFactory;

        private final SessionHolder sessionHolder;

        public SessionBindingCallableInterceptor(SessionFactory sessionFactory, SessionHolder sessionHolder) {
            this.sessionFactory = sessionFactory;
            this.sessionHolder = sessionHolder;
        }

        @Override
        public <T> void preProcess(NativeWebRequest request, Callable<T> task) {
            initializeThread();
        }

        @Override
        public <T> void postProcess(NativeWebRequest request, Callable<T> task, Object concurrentResult) {
            TransactionSynchronizationManager.unbindResource(this.sessionFactory);
        }

        private void initializeThread() {
            TransactionSynchronizationManager.bindResource(this.sessionFactory, this.sessionHolder);
        }
    }

}
