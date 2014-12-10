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

    private Order order = Order.asc(Criterion.get("name"));
    private PagingHelper pagingHelper = new PagingHelper();

    private Filter<Long> idFilter = Filter.get(Criterion.get("id"), RestrictionsExt.equal, Long.class, new Long(4));
    private Filter<String> nameFilter = Filter.get(Criterion.get("name"), RestrictionsExt.like, String.class, "name");
    private Filter<Integer> ageFilter = Filter.get(Criterion.get("age"), RestrictionsExt.equal, Integer.class, 1);

    private Filter<String> nameSubFilter = Filter.get(Criterion.get("nestedName", EntityAlias.SUBENTITIES), RestrictionsExt.like, String.class, "name");
    private Filter<Integer> ageSubFilter = Filter.get(Criterion.get("nestedAge", EntityAlias.SUBENTITIES), RestrictionsExt.equal, Integer.class, 1);

    @Override
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public PagingHelper getPagingHelper() {
        return pagingHelper;
    }

    public void setPagingHelper(PagingHelper pagingHelper) {
        this.pagingHelper = pagingHelper;
    }

    public Filter<String> getNameFilter() {
        return nameFilter;
    }

    public void setNameFilter(Filter<String> nameFilter) {
        this.nameFilter = nameFilter;
    }

    public Filter<Integer> getAgeFilter() {
        return ageFilter;
    }

    public void setAgeFilter(Filter<Integer> ageFilter) {
        this.ageFilter = ageFilter;
    }

    public Filter<String> getNameSubFilter() {
        return nameSubFilter;
    }

    public void setNameSubFilter(Filter<String> nameSubFilter) {
        this.nameSubFilter = nameSubFilter;
    }

    public Filter<Integer> getAgeSubFilter() {
        return ageSubFilter;
    }

    public void setAgeSubFilter(Filter<Integer> ageSubFilter) {
        this.ageSubFilter = ageSubFilter;
    }

    public Filter<Long> getIdFilter() {
        return idFilter;
    }

    public void setIdFilter(Filter<Long> idFilter) {
        this.idFilter = idFilter;
    }
}
