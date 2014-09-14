package com.anderl.domain;

import java.util.List;

/**
 * Created by dasanderl on 14.09.14.
 */
public class EntityBuilder {
    private String name;
    private int age;
    private List<NestedEntity> nestedEntitiesBatch10;
    private List<NestedEntity> nestedEntitiesNoBatch;
    private long id;

    private EntityBuilder() {
    }

    public static EntityBuilder aTestEntity() {
        return new EntityBuilder();
    }

    public EntityBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public EntityBuilder withAge(int age) {
        this.age = age;
        return this;
    }

    public EntityBuilder withNestedEntitiesBatch10(List<NestedEntity> nestedEntitiesBatch10) {
        this.nestedEntitiesBatch10 = nestedEntitiesBatch10;
        return this;
    }

    public EntityBuilder withNestedEntitiesNoBatch(List<NestedEntity> nestedEntitiesNoBatch) {
        this.nestedEntitiesNoBatch = nestedEntitiesNoBatch;
        return this;
    }

    public EntityBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public Entity build() {
        Entity entity = new Entity();
        entity.setName(name);
        entity.setAge(age);
        if (nestedEntitiesBatch10 != null)
            nestedEntitiesBatch10.forEach(nested -> nested.setEntityBatch10(entity));
        entity.setNestedEntitiesBatch10(nestedEntitiesBatch10);
        if (nestedEntitiesBatch10 != null)
            nestedEntitiesNoBatch.forEach(nested -> nested.setEntityNoBatch(entity));
        entity.setNestedEntitiesNoBatch(nestedEntitiesNoBatch);
        entity.setId(id);
        return entity;
    }
}
