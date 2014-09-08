package com.anderl.hibernate.ext;


import org.hibernate.criterion.Order;

/**
 * Created by ga2unte on 12/2/13.
 */
public class HibernateOrderWrapper {

    private boolean asc = true;
    private CriterionFieldJoinMappings.CriterionMapper criterionMapper;

    public HibernateOrderWrapper(CriterionFieldJoinMappings.CriterionMapper criterionMapper, boolean asc) {
        this.criterionMapper = criterionMapper;
        this.asc = asc;
    }

    public static HibernateOrderWrapper asc(CriterionFieldJoinMappings.CriterionMapper criterionMapper) {
        return new HibernateOrderWrapper(criterionMapper, true);
    }

    public static HibernateOrderWrapper desc(CriterionFieldJoinMappings.CriterionMapper criterionMapper) {
        return new HibernateOrderWrapper(criterionMapper, false);
    }

    public Order get() {
        if (asc) {
            return Order.asc(criterionMapper.getCriterionPath());
        }
        return Order.desc(criterionMapper.getCriterionPath());
    }

    public void set(CriterionFieldJoinMappings.CriterionMapper criterionMapper, boolean asc) {
        this.criterionMapper = criterionMapper;
        this.asc = asc;
    }

    public boolean isAsc() {
        return asc;
    }

    public void setAsc(boolean asc) {
        this.asc = asc;
    }

    public CriterionFieldJoinMappings.CriterionMapper getCriterionMapper() {
        return criterionMapper;
    }
}
