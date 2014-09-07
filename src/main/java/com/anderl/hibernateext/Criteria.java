package com.anderl.hibernateext;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 * Created by dasanderl on 06.09.14.
 */
public enum Criteria {

    eq {
        public <T> Criterion get(String property, T value) {
            return Restrictions.eq(property, value);
        }
    },
    ne {
        public <T> Criterion get(String property, T value) {
            return Restrictions.ne(property, value);
        }
    },
    isNull {
        public <T> Criterion get(String property, T value) {
            return Restrictions.isNull(property);
        }
    };

    public abstract <T> Criterion get(String property, T value);
}
