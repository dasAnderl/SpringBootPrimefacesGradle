package com.anderl.controller;

import com.anderl.dao.EntityRepository;
import com.anderl.domain.DomainProvider;
import com.anderl.domain.Entity;
import com.anderl.search.EntityFilter;
import com.anderl.search.PagingService;
import com.anderl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dasanderl on 07.09.14.
 */
@Controller
@Scope("session")
public class EntityController {


    @Autowired
    EntityRepository entityRepository;
    @Autowired
    PagingService<Entity> pagingService;
    @Autowired
    UserService userService;

    private EntityFilter entityFilter = new EntityFilter();
    private List<Entity> entities = new ArrayList<>();


    @Transactional
    public void saveTestEntity() {

        System.out.println("saving new entity");
        Entity entity = DomainProvider.getRandomEntity();
        entityRepository.save(entity);
    }

    @Transactional
    public void page() {
        entities = pagingService.page(entityFilter);
    }

    public String getUserName() {
        return userService.getUserNameFromService();
    }

    public EntityFilter getEntityFilter() {
        return entityFilter;
    }

    public void setEntityFilter(EntityFilter entityFilter) {
        this.entityFilter = entityFilter;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }
}
