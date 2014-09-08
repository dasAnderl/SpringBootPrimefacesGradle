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

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * This is an abstract only performing logic for urls which are not defined in its init ignoredUrls parameter
 * <p/>
 * <init-param>
 * <param-name>ignoredUrls</param-name>
 * <param-value>url1, url2, url3</param-value>
 * </init-param>
 */
public abstract class IgnorableFilter extends OncePerRequestFilter {

    public static Logger log = LoggerFactory.getLogger(IgnorableFilter.class);

    private List<String> ignoredUrls;

    public void initIgnoredUrls() {

        if (ignoredUrls != null) return;
        String ignoredUrlsString = getFilterConfig().getInitParameter("ignoredUrls");
        if (StringUtils.isEmpty(ignoredUrlsString)) return;
        this.ignoredUrls = Arrays.asList(ignoredUrlsString.split(","));
        this.ignoredUrls = Lists.newArrayList(
                Collections2.filter(ignoredUrls, new Predicate<String>() {
                    @Override
                    public boolean apply(String ignoredUrl) {
                        return StringUtils.isNotEmpty(ignoredUrl);
                    }
                })
        );
    }

    @Override
    protected boolean shouldNotFilterAsyncDispatch() {
        return false;
    }

    @Override
    protected boolean shouldNotFilterErrorDispatch() {
        return false;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (isIgnoredUrl(request)) {
            log.debug("{} has been ignored. see init params in web.xml", request.getServletPath());
            filterChain.doFilter(request, response);
        } else {
            doFilterUnignored(request, response, filterChain);
        }
    }

    protected abstract void doFilterUnignored(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException;

    protected boolean isIgnoredUrl(HttpServletRequest request) {
        String currentUrl = request.getServletPath();
        initIgnoredUrls();
        if (ignoredUrls == null) return false;
        for (String ignoredUrl : ignoredUrls) {
            if (currentUrl.startsWith(ignoredUrl)) return true;
        }
        return false;
    }
}
