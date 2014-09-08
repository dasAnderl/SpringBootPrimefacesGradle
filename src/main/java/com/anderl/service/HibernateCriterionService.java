package com.anderl.service;

import com.anderl.domain.TestEntity;
import com.anderl.hibernate.ext.HibernateCriterion;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dasanderl on 06.09.14.
 */
@Service
public class HibernateCriterionService {

    @Autowired
    EntityManager entityManager;

    private Criteria buildCriteria(List<HibernateCriterion> hibernateCriterions) {
        Assert.notEmpty(hibernateCriterions);
        Session session = entityManager.unwrap(Session.class);

        Criteria criteria = session.createCriteria(hibernateCriterions.get(0).entityType);
        List<Criterion> criterions = hibernateCriterions.stream().map(criterion -> criterion.get()).filter(criterion -> criterion != null).collect(Collectors.toList());
        criterions.stream().forEach(criterion -> criteria.add(criterion));
        return criteria;
    }

    @Transactional
    public List<TestEntity> getEntities(List<HibernateCriterion> hibernateCriterions) {
        return buildCriteria(hibernateCriterions).list();
    }

}
