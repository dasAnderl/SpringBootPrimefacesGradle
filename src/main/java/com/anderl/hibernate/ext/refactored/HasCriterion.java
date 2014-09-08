package com.anderl.hibernate.ext.refactored;

import com.anderl.hibernate.ext.HibernateCriterionOrWrapper;
import com.anderl.hibernate.ext.HibernateCriterionWrapper;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by ga2unte on 8.9.2014.
 */
public interface HasCriterion<T> {

    default List<HibernateCriterionWrapper> getCriterions() {
        return Helper.invokeGettersByReturnType(HibernateCriterionWrapper.class, this);
    }
}
