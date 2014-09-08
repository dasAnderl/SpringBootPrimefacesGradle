package com.anderl.hibernate.ext;


import com.anderl.hibernate.ext.refactored.Helper;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ga2unte on 12/2/13.
 * <p/>
 * To get a list of {@link org.hibernate.criterion.Criterion} for e.g. you searchcontroller:
 * <p/>
 * HibernateCriterionRetriever.getAllCriterionsFor(searchcontroller);
 * <p/>
 * To get a list of {@link org.hibernate.criterion.Order} for e.g. you searchcontroller:
 * <p/>
 * HibernateCriterionRetriever.getAllOrdersFor(searchcontroller);
 */
public class HibernateCriterionRetriever {

    public static List<HibernateCriterionWrapper> getWrappersRelevantForQuery(Object object) {
        List<HibernateCriterionWrapper> hibernateCriterionWrappers = Helper.invokeGettersByReturnType(HibernateCriterionWrapper.class, object);
        return Lists.newArrayList(
                Collections2.filter(hibernateCriterionWrappers, new Predicate<HibernateCriterionWrapper>() {
                    @Override
                    public boolean apply(HibernateCriterionWrapper hibernateCriterionWrapper) {
                        return hibernateCriterionWrapper.isValid();
                    }
                })
        );
    }

    public static List<HibernateCriterionOrWrapper> getOrWrappersRelevantForQuery(Object object) {
        List<HibernateCriterionOrWrapper> hibernateCriterionOrWrappers = Helper.invokeGettersByReturnType(HibernateCriterionOrWrapper.class, object);
        return Lists.newArrayList(
                Collections2.filter(hibernateCriterionOrWrappers, new Predicate<HibernateCriterionOrWrapper>() {
                    @Override
                    public boolean apply(HibernateCriterionOrWrapper hibernateCriterionOrWrapper) {
                        return hibernateCriterionOrWrapper.isValid();
                    }
                })
        );
    }

    public static List<HibernateCriterionWrapper> getWrappers(Object object, final boolean visible) {
        List<HibernateCriterionWrapper> hibernateCriterionWrappers = Helper.invokeGettersByReturnType(HibernateCriterionWrapper.class, object);
        return Lists.newArrayList(
                Collections2.filter(hibernateCriterionWrappers, new Predicate<HibernateCriterionWrapper>() {
                    @Override
                    public boolean apply(HibernateCriterionWrapper hibernateCriterionWrapper) {
                        return hibernateCriterionWrapper.isVisible() == visible;
                    }
                })
        );
    }


    public static List<CriterionFieldJoinMappings.AliasHolder> getDistinctAliases(PagingCriteria pagingCriteria) {

        List<CriterionFieldJoinMappings.AliasHolder> aliasesNotNull = new ArrayList<CriterionFieldJoinMappings.AliasHolder>();
        aliasesNotNull = addAliasesForWrappers(pagingCriteria.getWrappersRelevantForQuery(), aliasesNotNull);
        aliasesNotNull = addAliasesForOrWrappers(pagingCriteria.getOrWrappersRelevantForQuery(), aliasesNotNull);

        HibernateOrderWrapper orderWrapper = pagingCriteria.getOrderWrapper();
        if (orderWrapper != null && orderWrapper.getCriterionMapper().getAlias() != null) {
            aliasesNotNull.addAll(orderWrapper.getCriterionMapper().getAlias().getAliases());
        }

        List<CriterionFieldJoinMappings.AliasHolder> distinctAliases = Lists.newArrayList();
        for (CriterionFieldJoinMappings.AliasHolder aliasHolder : aliasesNotNull) {
            boolean missing = true;
            for (CriterionFieldJoinMappings.AliasHolder distinct : distinctAliases) {
                if (distinct.getPath().equals(aliasHolder.getPath())) {
                    missing = false;
                    break;
                }
            }
            if (missing) distinctAliases.add(aliasHolder);
        }
        return new ArrayList<CriterionFieldJoinMappings.AliasHolder>(distinctAliases);
    }

    private static List<CriterionFieldJoinMappings.AliasHolder> addAliasesForOrWrappers(List<HibernateCriterionOrWrapper> orWrappers, List<CriterionFieldJoinMappings.AliasHolder> aliases) {

        if (orWrappers == null) return Lists.newArrayList();
        List<List<HibernateCriterionWrapper>> wrappersLists = Lists.transform(orWrappers, new Function<HibernateCriterionOrWrapper, List<HibernateCriterionWrapper>>() {
            @Override
            public List<HibernateCriterionWrapper> apply(HibernateCriterionOrWrapper hibernateCriterionOrWrapper) {
                return hibernateCriterionOrWrapper.getHibernateCriterionWrappers();
            }
        });

        List<HibernateCriterionWrapper> wrappers = Lists.newArrayList(Iterables.concat(wrappersLists));
        return addAliasesForWrappers(wrappers, aliases);
    }

    public static List<CriterionFieldJoinMappings.AliasHolder> addAliasesForWrappers(List<HibernateCriterionWrapper> wrappers, List<CriterionFieldJoinMappings.AliasHolder> aliases) {

        if (wrappers == null) return Lists.newArrayList();
        List<CriterionFieldJoinMappings.CriterionMapper> criterionMappers = Lists.transform(wrappers, new Function<HibernateCriterionWrapper, CriterionFieldJoinMappings.CriterionMapper>() {
            @Override
            public CriterionFieldJoinMappings.CriterionMapper apply(HibernateCriterionWrapper wrapper) {
                return wrapper.getCriterionMapper();
            }
        });

        for (CriterionFieldJoinMappings.CriterionMapper criterionMapper : criterionMappers) {
            if (criterionMapper.getAlias() != null) {
                aliases.addAll(criterionMapper.getAlias().getAliases());
            }
        }
        return aliases;
    }
}
