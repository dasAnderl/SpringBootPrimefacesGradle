package com.anderl.dao;

import com.anderl.domain.Entity;
import org.springframework.data.repository.CrudRepository;

import javax.faces.bean.ManagedBean;
import java.util.List;

/**
 * Created by dasanderl on 07.09.14.
 */
@ManagedBean//only for autocompletion in xhtml. annotation not working
public interface EntityRepository extends CrudRepository<Entity, Long> {

    List<Entity> findByName(String name);

    List<Entity> findByAge(int age);

    List<Entity> findByNameAndAge(String name, int age);

    List<Entity> findByNameOrAge(String name, int age);


}
