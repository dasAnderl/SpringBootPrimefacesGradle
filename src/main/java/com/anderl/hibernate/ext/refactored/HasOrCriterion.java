package com.anderl.hibernate.ext.refactored;

import com.anderl.hibernate.ext.HibernateCriterionOrWrapper;
import com.anderl.hibernate.ext.HibernateCriterionWrapper;

import java.util.List;

/**
 * Created by ga2unte on 8.9.2014.
 */
public interface HasOrCriterion<T> {

    default List<HibernateCriterionOrWrapper> getOrCriterions() {
        return Helper.invokeGettersByReturnType(HibernateCriterionOrWrapper.class, this);
    }
}
