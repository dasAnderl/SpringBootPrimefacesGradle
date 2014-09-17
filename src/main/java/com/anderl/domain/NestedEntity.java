package com.anderl.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * Created by dasanderl on 14.09.14.
 */
@javax.persistence.Entity
public class NestedEntity extends _AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Entity entityBatch10;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Entity entityNoBatch;

    private String nestedName;
    private int nestedAge;

    public Entity getEntityBatch10() {
        return entityBatch10;
    }

    public void setEntityBatch10(Entity entityBatch10) {
        this.entityBatch10 = entityBatch10;
    }

    public Entity getEntityNoBatch() {
        return entityNoBatch;
    }

    public void setEntityNoBatch(Entity entityNoBatch) {
        this.entityNoBatch = entityNoBatch;
    }

    public String getNestedName() {
        return nestedName;
    }

    public void setNestedName(String nestedName) {
        this.nestedName = nestedName;
    }

    public int getNestedAge() {
        return nestedAge;
    }

    public void setNestedAge(int nestedAge) {
        this.nestedAge = nestedAge;
    }

    @Override
    public String toString() {
        return "NestedEntity{" +
                "id=" + getId() +
                ", nestedName='" + nestedName + '\'' +
                ", nestedAge=" + nestedAge +
                '}';
    }


}
