package com.anderl.domain;

/**
 * Created by dasanderl on 07.09.14.
 */
public class TestEntityBuilder {
    private String name;
    private int age;
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

    public TestEntityBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public TestEntity build() {
        TestEntity testEntity = new TestEntity();
        testEntity.setName(name);
        testEntity.setAge(age);
        testEntity.setId(id);
        return testEntity;
    }
}
