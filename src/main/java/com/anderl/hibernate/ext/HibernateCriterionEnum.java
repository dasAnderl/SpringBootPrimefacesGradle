package com.anderl.hibernate.ext;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by ga2unte on 12/3/13.
 */
public enum HibernateCriterionEnum {

    equal(false, false) {
        public Criterion get(String propertyName, Object value) {
            return Restrictions.eq(propertyName, value);
        }
    },
    notEqual(false, false) {
        public Criterion get(String propertyName, Object value) {
            return Restrictions.ne(propertyName, value);
        }
    },
    like(false, false) {
        public Criterion get(String propertyName, Object value) {
            Criterion ilike = Restrictions.ilike(propertyName, "%" + value + "%");
            return ilike;
        }
    },
    greaterThan(false, false) {
        public Criterion get(String propertyName, Object value) {
            return Restrictions.gt(propertyName, value);
        }
    },
    lessThan(false, false) {
        public Criterion get(String propertyName, Object value) {
            return Restrictions.lt(propertyName, value);
        }
    },
    greaterOrEqual(false, false) {
        public Criterion get(String propertyName, Object value) {
            return Restrictions.ge(propertyName, value);
        }
    },
    lessOrEqual(false, false) {
        public Criterion get(String propertyName, Object value) {
            return Restrictions.le(propertyName, value);
        }
    },
    in(false, true) {
        public Criterion get(String propertyName, Object values) {
            if (values == null || (!(values instanceof Collection) && !(values.getClass().isArray()))) {
                return Restrictions.in(propertyName, (Object[]) null);
            } else if (values.getClass().isArray()) {
                //TODO ga2unte: is this needed
                if ("id".toLowerCase().equals(propertyName) || propertyName.toLowerCase().endsWith(".id")) {
                    List<Object> valuelist = Arrays.asList((Object[]) values);
                    Long[] ids = new Long[valuelist.size()];
                    for (Object value : valuelist) {
                        ids[valuelist.indexOf(value)] = new Long(value.toString());
                    }
                    return Restrictions.in(propertyName, ids);
                }
                return Restrictions.in(propertyName, (Object[]) values);
            }
            return Restrictions.in(propertyName, ((Collection) values).toArray());
        }
    },
    notIn(false, true) {
        public Criterion get(String propertyName, Object values) {
            return Restrictions.not(in.get(propertyName, values));
        }
    },
    isNull(true, false) {
        public Criterion get(String propertyName, Object value) {
            return Restrictions.isNull(propertyName);
        }
    },
    isNotNull(true, false) {
        public Criterion get(String propertyName, Object value) {
            return Restrictions.isNotNull(propertyName);
        }
    };

    private boolean isNullValueAllowed;
    private boolean isMultiValue;

    HibernateCriterionEnum(boolean isNullAllowed, boolean isMultiValue) {
        this.isNullValueAllowed = isNullAllowed;
        this.isMultiValue = isMultiValue;
    }

    public abstract Criterion get(String propertyName, Object values);

    public boolean isNullValueAllowed() {
        return isNullValueAllowed;
    }

    public boolean isMultiValue() {
        return isMultiValue;
    }
}

