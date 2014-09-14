package com.anderl.domain;

/**
 * Created by dasanderl on 14.09.14.
 */
public class NestedEntityBuilder {
    private Entity entityBatch10;
    private Entity entityNoBatch;
    private String nestedName;
    private int nestedAge;
    private long id;

    private NestedEntityBuilder() {
    }

    public static NestedEntityBuilder aNestedEntity() {
        return new NestedEntityBuilder();
    }

    public NestedEntityBuilder withTestEntityBatch10(Entity entityBatch10) {
        this.entityBatch10 = entityBatch10;
        return this;
    }

    public NestedEntityBuilder withTestEntityNoBatch(Entity entityNoBatch) {
        this.entityNoBatch = entityNoBatch;
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
        nestedEntity.setEntityBatch10(entityBatch10);
        nestedEntity.setEntityNoBatch(entityNoBatch);
        nestedEntity.setNestedName(nestedName);
        nestedEntity.setNestedAge(nestedAge);
        nestedEntity.setId(id);
        return nestedEntity;
    }
}
