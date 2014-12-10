package com.anderl.search;

import com.anderl.hibernate.ext.AliasUtils;
import org.hibernate.sql.JoinType;

/**
 * Created by dasanderl on 09.12.14.
 */
public enum EntityAlias implements AliasUtils.Alias {

    SUBENTITIES("nestedEntitiesBatch10", JoinType.LEFT_OUTER_JOIN);

    private final String fieldPath;
    private final JoinType joinType;

    EntityAlias(String fieldPath, JoinType joinType) {

        this.fieldPath = fieldPath;
        this.joinType = joinType;
    }

    @Override
    public String getFieldPath() {
        return fieldPath;
    }

    @Override
    public JoinType getJoinType() {
        return joinType;
    }
}
