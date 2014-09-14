package com.anderl.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * Created by dasanderl on 14.09.14.
 */
@Entity
public class NestedEntity extends _AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private TestEntity testEntityBatch10;
    @ManyToOne(fetch = FetchType.LAZY)
    private TestEntity testEntityNoBatch;

    private String nestedName;
    private int nestedAge;

    public TestEntity getTestEntityBatch10() {
        return testEntityBatch10;
    }

    public void setTestEntityBatch10(TestEntity testEntityBatch10) {
        this.testEntityBatch10 = testEntityBatch10;
    }

    public TestEntity getTestEntityNoBatch() {
        return testEntityNoBatch;
    }

    public void setTestEntityNoBatch(TestEntity testEntityNoBatch) {
        this.testEntityNoBatch = testEntityNoBatch;
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
                "testEntityBatch10=" + testEntityBatch10 +
                ", testEntityNoBatch=" + testEntityNoBatch +
                ", nestedName='" + nestedName + '\'' +
                ", nestedAge='" + nestedAge + '\'' +
                '}';
    }
}
