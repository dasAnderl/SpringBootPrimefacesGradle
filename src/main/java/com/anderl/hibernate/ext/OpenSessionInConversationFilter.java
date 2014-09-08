package com.anderl.hibernate.ext;

import com.commerzbank.aop.utils.SpringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.async.WebAsyncManager;
import org.springframework.web.context.request.async.WebAsyncUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This is a modification of the @see org.springframework.orm.hibernate4.support.OpenSessionInViewFilter
 * This implements "open session in conversation" pattern.
 * This filter is configured in web.xml.
 * As long as the url of the current request is filtered by this filter
 * and there is no end conversation flag set, the very same hibernate session will be used.
 */
public class OpenSessionInConversationFilter extends IgnorableFilter {

    public static final String DEFAULT_SESSION_FACTORY_BEAN_NAME = "sessionFactory";

    private String sessionFactoryBeanName = DEFAULT_SESSION_FACTORY_BEAN_NAME;

    public static Logger log = LoggerFactory.getLogger(OpenSessionInConversationFilter.class);

    public void setSessionFactoryBeanName(String sessionFactoryBeanName) {
        this.sessionFactoryBeanName = sessionFactoryBeanName;
    }

    protected String getSessionFactoryBeanName() {
        return this.sessionFactoryBeanName;
    }


    @Override
    protected void doFilterUnignored(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {


        SessionFactory sessionFactory = SpringUtils.getSpringBeanFromWebCtx(SessionFactory.class);

        WebAsyncManager asyncManager = WebAsyncUtils.getAsyncManager(request);
        String key = getAlreadyFilteredAttributeName();

        boolean isFirstRequest = !isAsyncDispatch(request);
        if (isFirstRequest || !OpenSessionHelper.applySessionBindingInterceptor(asyncManager, key)) {

            Session session = OpenSessionHelper.getSession(request, key, sessionFactory);
            OpenSessionHelper.bindSessionToTransactionSnyManager(sessionFactory, asyncManager, key, session);
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            //if conversation end
            if (OpenSessionHelper.isEndConversationFlagSet(request)) {
                OpenSessionHelper.endConversation(sessionFactory, request);
            } else {
                //else only unbind session
                log.debug("not closing session: extended to conversation");
                OpenSessionHelper.unbindSession(sessionFactory);
            }
        }
    }
}
