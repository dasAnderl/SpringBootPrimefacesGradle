package com.anderl.dao;

import com.anderl.domain.TestEntity;
import org.springframework.data.repository.CrudRepository;

import javax.faces.bean.ManagedBean;
import java.util.List;

/**
 * Created by dasanderl on 07.09.14.
 */
@ManagedBean//only for autocompletion in xhtml. annotation not working
public interface TestEntityRepository extends CrudRepository<TestEntity, Long> {

    List<TestEntity> findByName(String name);

    List<TestEntity> findByAge(int age);

    List<TestEntity> findByNameAndAge(String name, int age);

    List<TestEntity> findByNameOrAge(String name, int age);


}
