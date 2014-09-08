package com.anderl.hibernate.ext;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
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
    },
    like {
        public <T> Criterion get(String property, T value) {
            return Restrictions.ilike(property, (String) value, MatchMode.ANYWHERE);
        }
    };

    public abstract <T> Criterion get(String property, T value);
}
