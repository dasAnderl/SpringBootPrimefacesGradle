package com.anderl.domain;

import org.hibernate.annotations.BatchSize;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by dasanderl on 07.09.14.
 */
@javax.persistence.Entity
public class Entity extends _AbstractEntity {

    private String name;
    private int age;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "entityBatch10", cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 10)
    private List<NestedEntity> nestedEntitiesBatch10;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "entityNoBatch", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NestedEntity> nestedEntitiesNoBatch;

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

    public List<NestedEntity> getNestedEntitiesBatch10() {
        return nestedEntitiesBatch10;
    }

    public void setNestedEntitiesBatch10(List<NestedEntity> nestedEntitiesBatch10) {
        this.nestedEntitiesBatch10 = nestedEntitiesBatch10;
    }

    public List<NestedEntity> getNestedEntitiesNoBatch() {
        return nestedEntitiesNoBatch;
    }

    public void setNestedEntitiesNoBatch(List<NestedEntity> nestedEntitiesNoBatch) {
        this.nestedEntitiesNoBatch = nestedEntitiesNoBatch;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", nestedEntitiesBatch10=" + nestedEntitiesBatch10 +
                ", nestedEntitiesNoBatch=" + nestedEntitiesNoBatch +
                '}';
    }
}
