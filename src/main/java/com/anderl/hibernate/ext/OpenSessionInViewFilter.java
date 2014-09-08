/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
 * Most taken from
 *
 * @see org.springframework.orm.hibernate4.support.OpenSessionInViewFilter
 * Modifications made to enable the hibernate session to stay open over several requests.
 */
public class OpenSessionInViewFilter extends IgnorableFilter {

    public static Logger log = LoggerFactory.getLogger(OpenSessionInViewFilter.class);

    public static final String DEFAULT_SESSION_FACTORY_BEAN_NAME = "sessionFactory";
    private String sessionFactoryBeanName = DEFAULT_SESSION_FACTORY_BEAN_NAME;

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

        SessionFactory sessionFactory = SpringUtils.getSpringBeanFromWebCtx(sessionFactoryBeanName, SessionFactory.class);

        WebAsyncManager asyncManager = WebAsyncUtils.getAsyncManager(request);
        String key = getAlreadyFilteredAttributeName();

        boolean isFirstRequest = !isAsyncDispatch(request);
        //TODO ga2unte: this doesnt work, because isExtendedToNextRequest is always false if set in last request
        //possible fix to ensure session cleanup: create two sessions in sesionholder, one for extended requests, one for conversation
        //then here clean only conversation session
//            //if not extended to next request
//            if (!OpenSessionHelper.isExtendedToNextRequest(request)) {
//                //if there is still a session in sessionholder
//                if (OpenSessionHelper.getSessionFromCache() != null) {
//                    //close that session
//                    //example: user comes from conversation, session was set in OpenSessionInConversationFilter
//                    //but user has navigated away from that conversation without setting ending the conversation
//                    OpenSessionHelper.closeSessionFromCache();
//                }
//            }
        if (isFirstRequest || !OpenSessionHelper.applySessionBindingInterceptor(asyncManager, key)) {

            Session session = OpenSessionHelper.getSession(request, key, sessionFactory);
            OpenSessionHelper.bindSessionToTransactionSnyManager(sessionFactory, asyncManager, key, session);
        }

        try {
            filterChain.doFilter(request, response);
        } finally {

            //always unbind session
            OpenSessionHelper.unbindSession(sessionFactory);
            //if not extended to next request, close session
            if (!OpenSessionHelper.isExtendedToNextRequest(request)) {
                OpenSessionHelper.closeSessionFromCache();
            } else {
                log.debug("not closing session: extended to nextt request");
            }
        }
    }
}
