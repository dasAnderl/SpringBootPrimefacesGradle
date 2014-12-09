package com.anderl.controller;

import com.anderl.dao.EntityRepository;
import com.anderl.domain.DomainProvider;
import com.anderl.domain.Entity;
import com.anderl.hibernate.ext.PagingHelper;
import com.anderl.hibernate.ext.RestrictionsExt;
import com.anderl.hibernate.ext.SearchFilter;
import com.anderl.hibernate.ext.wrappers.Filter;
import com.anderl.hibernate.ext.wrappers.Order;
import com.anderl.search.EntityAlias;
import com.anderl.search.PagingService;
import com.anderl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.faces.bean.ManagedBean;
import java.util.ArrayList;
import java.util.List;

import static com.anderl.hibernate.ext.AliasUtils.Criterion;

/**
 * Created by dasanderl on 07.09.14.
 */
@Component
@Scope("view")
@ManagedBean//only for autocompletion in xhtml. annotation not working
public class EntityController implements SearchFilter<Entity> {


    @Autowired
    EntityRepository entityRepository;
    @Autowired
    PagingService<Entity> pagingService;
    @Autowired
    UserService userService;

    private Order order = Order.asc(Criterion.get("name"));
    private PagingHelper pagingHelper = new PagingHelper();

    private Filter<String> nameFilter = new Filter<>(Criterion.get("name"), RestrictionsExt.like, "name");
    private Filter<Integer> ageFilter = new Filter<>(Criterion.get("age"), RestrictionsExt.equal, 1);

    private Filter<String> nameSubFilter = new Filter<>(Criterion.get("name", EntityAlias.SUBENTITIES), RestrictionsExt.like, "name");
    private Filter<Integer> ageSubFilter = new Filter<>(Criterion.get("age", EntityAlias.SUBENTITIES), RestrictionsExt.equal, 1);

    private List<Entity> entities = new ArrayList<>();

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

    @Transactional
    public void saveTestEntity() {

        System.out.println("saving new entity");
        Entity entity = DomainProvider.getRandomEntity();
        entityRepository.save(entity);
    }

    @Transactional
    public void page() {
        entities = pagingService.page(this);
    }

    public String getUserName() {
        return userService.getUserNameFromService();
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

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
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
}
