package com.anderl.hibernateext;

import org.hibernate.criterion.Criterion;
import org.springframework.util.Assert;

import java.util.logging.Logger;

/**
 * Created by dasanderl on 06.09.14.
 */
public class HibernateCriterion<T, E> {

    private final static Logger log = Logger.getLogger(HibernateCriterion.class.getSimpleName());

    public final Class<T> valueType;
    public final Class<E> entityType;
    public final String property;
    public final Criteria criteria;
    private T value;
    private boolean enabled = true;

    public HibernateCriterion(Class<T> valueType, Class<E> entityType, String property, Criteria criteria) {
        this.valueType = valueType;
        this.entityType = entityType;
        this.property = property;
        this.criteria = criteria;
        assertFields();
    }

    public HibernateCriterion(Class<T> valueType, Class<E> entityType, String property, Criteria criteria, T value) {
        this.valueType = valueType;
        this.entityType = entityType;
        this.property = property;
        this.criteria = criteria;
        this.value = value;
        assertFields();
    }

    public HibernateCriterion(Class<E> entityType, String property) {
        this.valueType = (Class<T>) String.class;
        this.entityType = entityType;
        this.property = property;
        this.criteria = Criteria.eq;
        assertFields();
    }

    public Criterion get() {
        if (!isValid()) return null;
        return criteria.get(property, value);
    }

    public boolean isValid() {
        boolean valid = true;
        final boolean valueRequired = !criteria.equals(Criteria.isNull);
        if (valueRequired) valid = value != null;
        if (!Helper.fieldExists(entityType, property))
            throw new RuntimeException("No field " + property + " found on " + entityType.getSimpleName());
        return valid;
    }

    private void assertFields() {
        Assert.notNull(valueType);
        Assert.notNull(entityType);
        Assert.notNull(property);
        Assert.notNull(criteria);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

}
