package com.anderl.search;

import com.anderl.domain.Entity;
import com.anderl.hibernate.ext.PagingHelper;
import com.anderl.hibernate.ext.RestrictionsExt;
import com.anderl.hibernate.ext.SearchFilter;
import com.anderl.hibernate.ext.wrappers.Filter;
import com.anderl.hibernate.ext.wrappers.Order;

import static com.anderl.hibernate.ext.AliasUtils.Criterion;

/**
 * Created by dasanderl on 09.12.14.
 */
public class EntityFilter implements SearchFilter<Entity> {
    Filter<String> nameFilter = new Filter<>(Criterion.get("name"), RestrictionsExt.like, "name");

    @Override
    public Order getOrder() {
        return Order.asc(Criterion.get("name"));
    }

    @Override
    public PagingHelper getPagingHelper() {
        return new PagingHelper();
    }

    public Filter<String> getNameFilter() {
        return nameFilter;
    }

    public void setNameFilter(Filter<String> nameFilter) {
        this.nameFilter = nameFilter;
    }
}
