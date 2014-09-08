package com.anderl.hibernate.ext;

import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ga2unte on 12/19/13.
 */
public class CriteriaHelper {

    public static List<Object> getIdsFromResultSet(boolean hasOrder, List result) {
        List<Object> ids = new ArrayList<Object>();
        //if we have an order, resultset is different, because of projection needed to sort.
        if (hasOrder) {
            for (Object resultEntry : result) {
                Object[] resultEntryArray = (Object[]) resultEntry;
                ids.add(resultEntryArray[0]);
            }

        }
        //if we dont have an order, resultset is list of ids, because we added no projection to be able to sort
        else {
            ids.addAll(result);
        }
        return ids;
    }

    /**
     * Adds rowcount and distinct id projection to criteria.
     * BE VERY CAREFUL CHANGING THIS.
     *
     * @param criteria
     * @return
     */
    public static Criteria addCountDistinctIdProjections(Criteria criteria) {
        return criteria.setProjection(Projections.rowCount())
                .setProjection(Projections.distinct
                        (Projections.countDistinct("id")));
    }

    /**
     * Adds distinct id projection to criteria.
     * Also adds projection for sorted column, which is needed to make ordering working.
     * BE VERY CAREFUL CHANGING THIS.
     *
     * @param criteria
     * @param orderWrapper
     * @return
     */
    public static void addDistinctIdAndOrderProjections(Criteria criteria, HibernateOrderWrapper orderWrapper) {

        ProjectionList projectionList = Projections.projectionList();
        projectionList
                .add(Projections.distinct
                        (Projections.property("id")));
        if (orderWrapper != null) {
            projectionList.add(Projections.property(orderWrapper.getCriterionMapper().getCriterionPath()));
        }
        criteria.setProjection(projectionList);
    }
}
