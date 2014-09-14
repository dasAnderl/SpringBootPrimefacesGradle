package com.anderl.domain;

import com.google.common.collect.Lists;
import org.hibernate.annotations.BatchSize;
import org.springframework.util.CollectionUtils;

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
    private List<NestedEntity> nestedEntitiesBatch10 = Lists.newArrayList();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "entityNoBatch", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NestedEntity> nestedEntitiesNoBatch = Lists.newArrayList();

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
        if (nestedEntitiesBatch10 == null) nestedEntitiesBatch10 = Lists.newArrayList();
        return nestedEntitiesBatch10;
    }

    public void setNestedEntitiesBatch10(List<NestedEntity> nestedEntitiesBatch10) {
        if (!CollectionUtils.isEmpty(nestedEntitiesBatch10))
            nestedEntitiesBatch10.stream().forEach(nested -> nested.setEntityBatch10(this));
        this.nestedEntitiesBatch10 = nestedEntitiesBatch10;
    }

    public List<NestedEntity> getNestedEntitiesNoBatch() {
        if (nestedEntitiesNoBatch == null) nestedEntitiesNoBatch = Lists.newArrayList();
        return nestedEntitiesNoBatch;
    }

    public void setNestedEntitiesNoBatch(List<NestedEntity> nestedEntitiesNoBatch) {
        if (!CollectionUtils.isEmpty(nestedEntitiesNoBatch))
            nestedEntitiesNoBatch.stream().forEach(nested -> nested.setEntityNoBatch(this));
        this.nestedEntitiesNoBatch = nestedEntitiesNoBatch;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "id='" + getId() + '\'' +
                "name='" + name + '\'' +
                ", age=" + age +
                ", nestedEntitiesBatch10=" + nestedEntitiesBatch10 +
                ", nestedEntitiesNoBatch=" + nestedEntitiesNoBatch +
                '}';
    }
}
