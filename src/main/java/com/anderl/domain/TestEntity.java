package com.anderl.domain;

import javax.persistence.Entity;

/**
 * Created by dasanderl on 07.09.14.
 */
@Entity
public class TestEntity extends _AbstractEntity {

    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "TestEntity{" +
                "id='" + getId() + '\'' +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
