package com.anderl.domain;

import java.util.List;

/**
 * Created by dasanderl on 14.09.14.
 */
public class TestEntityBuilder {
    private String name;
    private int age;
    private List<NestedEntity> nestedEntitiesBatch10;
    private List<NestedEntity> nestedEntitiesNoBatch;
    private long id;

    private TestEntityBuilder() {
    }

    public static TestEntityBuilder aTestEntity() {
        return new TestEntityBuilder();
    }

    public TestEntityBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public TestEntityBuilder withAge(int age) {
        this.age = age;
        return this;
    }

    public TestEntityBuilder withNestedEntitiesBatch10(List<NestedEntity> nestedEntitiesBatch10) {
        this.nestedEntitiesBatch10 = nestedEntitiesBatch10;
        return this;
    }

    public TestEntityBuilder withNestedEntitiesNoBatch(List<NestedEntity> nestedEntitiesNoBatch) {
        this.nestedEntitiesNoBatch = nestedEntitiesNoBatch;
        return this;
    }

    public TestEntityBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public TestEntity build() {
        TestEntity testEntity = new TestEntity();
        testEntity.setName(name);
        testEntity.setAge(age);
        testEntity.setNestedEntitiesBatch10(nestedEntitiesBatch10);
        testEntity.setNestedEntitiesNoBatch(nestedEntitiesNoBatch);
        testEntity.setId(id);
        return testEntity;
    }
}
