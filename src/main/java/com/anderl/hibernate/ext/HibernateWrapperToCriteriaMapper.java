package com.anderl.hibernate.ext;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by ga2unte on 11/22/13.
 */
public class HibernateWrapperToCriteriaMapper {

    public static Criteria addCriterionWrappers(Criteria criteria,
                                                List<HibernateCriterionWrapper> criterionWrappers,
                                                List<HibernateCriterionOrWrapper> criterionOrWrappers,
                                                HibernateOrderWrapper orderWrapper) {
        Criterion andCriterion = null;
        if (!CollectionUtils.isEmpty(criterionWrappers)) {

            List<Criterion> criterions = Lists.transform(criterionWrappers, new Function<HibernateCriterionWrapper, Criterion>() {
                @Override
                public Criterion apply(HibernateCriterionWrapper wrapper) {
                    return wrapper.getCriterion();
                }
            });

            andCriterion = Restrictions.and(criterions.toArray(new Criterion[criterions.size()]));
        }

        if (CollectionUtils.isEmpty(criterionOrWrappers) && andCriterion != null) {
            criteria.add(andCriterion);
        } else {

            List<Criterion> orCriterions = Lists.newArrayList(Lists.transform(criterionOrWrappers, new Function<HibernateCriterionOrWrapper, Criterion>() {
                @Override
                public Criterion apply(HibernateCriterionOrWrapper hibernateCriterionOrWrapper) {
                    return hibernateCriterionOrWrapper.getCriterion();
                }
            }));

            if (!CollectionUtils.isEmpty(orCriterions)) {
                if (andCriterion != null) {
                    orCriterions.add(0, andCriterion);
                }
                Criterion orCriterion = Restrictions.and(orCriterions.toArray(new Criterion[orCriterions.size()]));
                criteria.add(orCriterion);
            }
        }
        // We only add orders, if they are present and this is not a count query
        if (orderWrapper != null) {
            criteria.addOrder(orderWrapper.get());
        }
        return criteria;
    }

    public static Criteria addCriterionWrappers(Criteria criteria,
                                                PagingCriteria pagingCriteria) {
        return addCriterionWrappers(criteria, pagingCriteria.getWrappersRelevantForQuery(), pagingCriteria.getOrWrappersRelevantForQuery(), pagingCriteria.getOrderWrapper());
    }
}
