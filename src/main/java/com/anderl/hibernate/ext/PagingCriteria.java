package com.anderl.hibernate.ext;

import java.util.List;

/**
 * Created by ga2unte on 12/19/13.
 */
public interface PagingCriteria<T> {

    public List<HibernateCriterionWrapper<T>> getWrappersRelevantForQuery();

    public List<HibernateCriterionOrWrapper> getOrWrappersRelevantForQuery();

    public List<HibernateCriterionWrapper> getVisibleWrappers();

    public HibernateOrderWrapper getOrderWrapper();

    public Class getType();
}
