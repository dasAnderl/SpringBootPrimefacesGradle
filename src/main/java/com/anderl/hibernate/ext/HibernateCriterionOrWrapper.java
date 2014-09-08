package com.anderl.hibernate.ext;


import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by ga2unte on 12/2/13.
 * <p/>
 * An  {@link HibernateCriterionOrWrapper} contains multiple  {@link HibernateCriterionWrapper}.
 * They will be written to a map and later cocatenated with or.
 * Never use same property for two different {@link HibernateCriterionWrapper}s.
 * To do this use {@link HibernateCriterionEnum}in.
 * <p/>
 * Example: You want to have a Criterion which selects entity which id "is not null OR name is null"
 * You would use:
 * HibernateCriterionOrWrapper orWrapper = new HibernateCriterionOrWrapper(
 * new HibernateCriterionWrapper("id", null, HibernateCriterionEnum.isNull),
 * new HibernateCriterionWrapper("name", null, HibernateCriterionEnum.isNotNull)
 * );
 * orWrapper.get();
 * would give a List with one hibernate {@link org.hibernate.criterion.Restrictions}:
 * Restrictions.and(Restrictions.or(Restrictions.isNotNull("id"), Restrictions.isNull("name")))
 * <p/>
 * In xhtml you can access the values of the contained {@link HibernateCriterionWrapper},
 * by calling #{<controller>.<myHibernateCriterionOrWrapper>.getByProperty("id").value}
 */
public class HibernateCriterionOrWrapper implements ColumnControl {

    private static Logger log = LoggerFactory.getLogger(HibernateCriterionOrWrapper.class);

    //Defines how the subcriterias are appended to the query:
    // true -> ... AND (subcriteria)
    // false -> ... OR (subcriteria)
    private boolean outerConcatAnd = true;
    private boolean innerAndConcat;
    private HibernateCriterionWrapper firstWrapper;
    private String id;
    private boolean visible;
    private Map<String, HibernateCriterionWrapper<Object>> wrappersMappedByProperty = new HashMap<String, HibernateCriterionWrapper<Object>>();

    private HibernateCriterionOrWrapper(boolean outerConcatAnd, boolean innerAndConcat, HibernateCriterionWrapper... hibernateCriterionWrappers) {
        this.outerConcatAnd = outerConcatAnd;
        this.innerAndConcat = innerAndConcat;
        //Attention: varargs order is reversed
        for (HibernateCriterionWrapper criterionWrapper : hibernateCriterionWrappers) {
            firstWrapper = criterionWrapper;
            this.id = "multiCriterion" + firstWrapper.getCriterionMapper().getCriterionPath().replace(".", "");
            String property = criterionWrapper.getCriterionMapper().getCriterionPath();
            if (wrappersMappedByProperty.containsKey(property)) {
                log.error("{} used with same property more than once. use {} instead. You query will not return correct results", this.getClass().getSimpleName(), HibernateCriterionEnum.in);
                this.wrappersMappedByProperty = new HashMap<String, HibernateCriterionWrapper<Object>>();
                continue;
            }
            wrappersMappedByProperty.put(property, criterionWrapper);
        }
    }

    public static HibernateCriterionOrWrapper andOr(HibernateCriterionWrapper... hibernateCriterionWrappers) {
        return new HibernateCriterionOrWrapper(true, false, hibernateCriterionWrappers);
    }

    public static HibernateCriterionOrWrapper orOr(HibernateCriterionWrapper... hibernateCriterionWrappers) {
        return new HibernateCriterionOrWrapper(false, false, hibernateCriterionWrappers);
    }

    public static HibernateCriterionOrWrapper andAnd(HibernateCriterionWrapper... hibernateCriterionWrappers) {
        return new HibernateCriterionOrWrapper(true, true, hibernateCriterionWrappers);
    }

    public static HibernateCriterionOrWrapper orAnd(HibernateCriterionWrapper... hibernateCriterionWrappers) {
        return new HibernateCriterionOrWrapper(false, true, hibernateCriterionWrappers);
    }

    public CriterionFieldJoinMappings.CriterionMapper getCriterionMapper() {
        return firstWrapper.getCriterionMapper();
    }

    public Criterion getCriterion() {

        Predicate<HibernateCriterionWrapper> isValid = new Predicate<HibernateCriterionWrapper>() {
            @Override
            public boolean apply(HibernateCriterionWrapper hibernateCriterionWrapper) {
                return hibernateCriterionWrapper.isValid();
            }
        };
        Collection<HibernateCriterionWrapper<Object>> validCriterionWrappers = Collections2.filter(getWrappersMappedByProperty().values(), isValid);

        Function<HibernateCriterionWrapper, Criterion> getCriterions = new Function<HibernateCriterionWrapper, Criterion>() {
            @Override
            public Criterion apply(HibernateCriterionWrapper hibernateCriterionOrWrapper) {
                return hibernateCriterionOrWrapper.getCriterion();
            }
        };
        List<HibernateCriterionWrapper<Object>> validCriterionWrappersList = Lists.newArrayList(validCriterionWrappers);
        List<Criterion> validCriterions = Lists.transform(validCriterionWrappersList, getCriterions);

        if (CollectionUtils.isEmpty(validCriterions)) return null;
        Criterion[] predicates = validCriterions.toArray(new Criterion[validCriterions.size()]);
        Criterion junction;
        if (outerConcatAnd && innerAndConcat) {
            junction = Restrictions.and(Restrictions.and(predicates));
        } else if (outerConcatAnd && !innerAndConcat) {
            junction = Restrictions.and(Restrictions.or(predicates));
        } else if (!outerConcatAnd && innerAndConcat) {
            junction = Restrictions.or(Restrictions.and(predicates));
        } else {
            junction = Restrictions.or(Restrictions.or(predicates));
        }
        return junction;
    }

    private void setValueForAllCriterions(Object value) {

        for (HibernateCriterionWrapper criterionWrapper : wrappersMappedByProperty.values()) {
            criterionWrapper.setValue(value);
        }
    }

    public boolean isValid() {
        return getCriterion() != null;
    }

    /**
     * Use this method only if you want to use same value for all criterions in this orWrapper.
     * Otherwise use getByProperty to access value of specific criterion.
     *
     * @param value
     */
    @Override
    public void setValue(Object value) {
        setValueForAllCriterions(value);
    }

    /**
     * Return property of the first {@link HibernateCriterionWrapper}
     *
     * @return
     */
    @Override
    public String getSortingProperty() {
        return firstWrapper.getCriterionMapper().getCriterionPath();
    }

    /**
     * Use this method only if you want to use same value for all criterions in this orWrapper.
     * Otherwise use getByProperty to access value of specific criterion.
     */
    @Override
    public Object getValue() {
        return getWrappersMappedByProperty().values().isEmpty() ? null : getWrappersMappedByProperty().values().iterator().next().getValue();
    }

    @Override
    public String getId() {
        return id;
    }

    public Map<String, HibernateCriterionWrapper<Object>> getWrappersMappedByProperty() {
        return wrappersMappedByProperty;
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    @Override
    public void setVisible(boolean active) {
        this.visible = active;
    }

    @Override
    public String getLabelMsgKey() {
        return firstWrapper.getLabelMsgKey();
    }

    public List<HibernateCriterionWrapper> getHibernateCriterionWrappers() {
        return CollectionUtils.isEmpty(getWrappersMappedByProperty().values()) ? new ArrayList<HibernateCriterionWrapper>()
                : new ArrayList<HibernateCriterionWrapper>(getWrappersMappedByProperty().values());
    }
}
