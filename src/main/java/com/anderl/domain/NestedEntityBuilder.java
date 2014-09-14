package com.anderl.domain;

/**
 * Created by dasanderl on 14.09.14.
 */
public class NestedEntityBuilder {
    private TestEntity testEntityBatch10;
    private TestEntity testEntityNoBatch;
    private String nestedName;
    private int nestedAge;
    private long id;

    private NestedEntityBuilder() {
    }

    public static NestedEntityBuilder aNestedEntity() {
        return new NestedEntityBuilder();
    }

    public NestedEntityBuilder withTestEntityBatch10(TestEntity testEntityBatch10) {
        this.testEntityBatch10 = testEntityBatch10;
        return this;
    }

    public NestedEntityBuilder withTestEntityNoBatch(TestEntity testEntityNoBatch) {
        this.testEntityNoBatch = testEntityNoBatch;
        return this;
    }

    public NestedEntityBuilder withNestedName(String nestedName) {
        this.nestedName = nestedName;
        return this;
    }

    public NestedEntityBuilder withNestedAge(int nestedAge) {
        this.nestedAge = nestedAge;
        return this;
    }

    public NestedEntityBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public NestedEntity build() {
        NestedEntity nestedEntity = new NestedEntity();
        nestedEntity.setTestEntityBatch10(testEntityBatch10);
        nestedEntity.setTestEntityNoBatch(testEntityNoBatch);
        nestedEntity.setNestedName(nestedName);
        nestedEntity.setNestedAge(nestedAge);
        nestedEntity.setId(id);
        return nestedEntity;
    }
}
