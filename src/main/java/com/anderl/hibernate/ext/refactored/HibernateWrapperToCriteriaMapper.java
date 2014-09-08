package com.anderl.hibernate.ext.refactored;

import com.anderl.hibernate.ext.HibernateCriterionOrWrapper;
import com.anderl.hibernate.ext.HibernateCriterionWrapper;
import com.anderl.hibernate.ext.HibernateOrderWrapper;
import com.anderl.hibernate.ext.PagingCriteria;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ga2unte on 11/22/13.
 */
public class HibernateWrapperToCriteriaMapper {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(HibernateWrapperToCriteriaMapper.class);


    public static Criteria createCriteria(HasCriteria hasCriteria, Session session) {

        session.createCriteria(hasCriteria.getType());
        Criterion andCriterion = null;

        List<HibernateCriterionWrapper> criterions = hasCriteria.getCriterions();
        if (!CollectionUtils.isEmpty(criterions)) {
            final List<HibernateCriterionWrapper> validCriterions = criterions.stream()
                    .filter(criterion -> validateAndCastProperty(criterion, hasCriteria.getType()))
                    .filter(criterion -> criterion.isValid())
                    .filter(criterion -> criterion.isVisible())
                    .collect(Collectors.toList());
            andCriterion = Restrictions.and(validCriterions.toArray(new Criterion[validCriterions.size()]));
        }
        return null;
//
//            List<Criterion> criterions = Lists.transform(criterionWrappers, new Function<HibernateCriterionWrapper, Criterion>() {
//                @Override
//                public Criterion apply(HibernateCriterionWrapper wrapper) {
//                    return wrapper.getCriterion();
//                }
//            });
//
//            andCriterion = Restrictions.and(criterions.toArray(new Criterion[criterions.size()]));
//        }
    }

    private static boolean validateAndCastProperty(HibernateCriterionWrapper criterion, Class clazz) {
        final String fullPropertyPath = criterion.getCriterionMapper().getCriterionPath();
        final ArrayList<String> fullPropertyPaths = Lists.newArrayList(fullPropertyPath.split("."));
        Class currentClass = clazz;
        while (fullPropertyPaths.iterator().hasNext()) {
            final String prop = fullPropertyPaths.iterator().next();
            final Field field = ReflectionUtils.findField(currentClass, prop);
            if (field == null) {
                log.error("WRONG CRITERION: {} is not a field on {} ({})", prop, currentClass.getSimpleName(), fullPropertyPath);
                return false;
            }
            currentClass = field.getType();
        }
        if(criterion.getValue() != null)criterion.setValue(currentClass.cast(criterion.getValue()));
        return true;
    }

//
//    public static Criteria addCriterionWrappers(Criteria criteria,
//                                                List<HibernateCriterionWrapper> criterionWrappers,
//                                                List<HibernateCriterionOrWrapper> criterionOrWrappers,
//                                                HibernateOrderWrapper orderWrapper) {
//        Criterion andCriterion = null;
//        if (!CollectionUtils.isEmpty(criterionWrappers)) {
//
//            List<Criterion> criterions = Lists.transform(criterionWrappers, new Function<HibernateCriterionWrapper, Criterion>() {
//                @Override
//                public Criterion apply(HibernateCriterionWrapper wrapper) {
//                    return wrapper.getCriterion();
//                }
//            });
//
//            andCriterion = Restrictions.and(criterions.toArray(new Criterion[criterions.size()]));
//        }
//
//        if (CollectionUtils.isEmpty(criterionOrWrappers) && andCriterion != null) {
//            criteria.add(andCriterion);
//        } else {
//
//            List<Criterion> orCriterions = Lists.newArrayList(Lists.transform(criterionOrWrappers, new Function<HibernateCriterionOrWrapper, Criterion>() {
//                @Override
//                public Criterion apply(HibernateCriterionOrWrapper hibernateCriterionOrWrapper) {
//                    return hibernateCriterionOrWrapper.getCriterion();
//                }
//            }));
//
//            if (!CollectionUtils.isEmpty(orCriterions)) {
//                if (andCriterion != null) {
//                    orCriterions.add(0, andCriterion);
//                }
//                Criterion orCriterion = Restrictions.and(orCriterions.toArray(new Criterion[orCriterions.size()]));
//                criteria.add(orCriterion);
//            }
//        }
//        // We only add orders, if they are present and this is not a count query
//        if (orderWrapper != null) {
//            criteria.addOrder(orderWrapper.get());
//        }
//        return criteria;
//    }
//
//    public static Criteria addCriterionWrappers(Criteria criteria,
//                                                PagingCriteria pagingCriteria) {
//        return addCriterionWrappers(criteria, pagingCriteria.getWrappersRelevantForQuery(), pagingCriteria.getOrWrappersRelevantForQuery(), pagingCriteria.getOrderWrapper());
//    }
}
