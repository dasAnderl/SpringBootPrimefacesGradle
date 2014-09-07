package com.anderl.domain;

import org.springframework.boot.orm.jpa.EntityScan;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by dasanderl on 07.09.14.
 */
@Entity
public class TestEntity {

   @Id
   @GeneratedValue(strategy= GenerationType.AUTO)
   private long id;

    private String name;
    private int age;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
}
