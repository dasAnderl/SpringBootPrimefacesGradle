package com.anderl.dao;

import com.anderl.domain.User;
import org.springframework.data.repository.CrudRepository;

import javax.faces.bean.ManagedBean;
import java.util.List;

/**
 * Created by dasanderl on 07.09.14.
 */
@ManagedBean//only for autocompletion in xhtml. annotation not working
public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findByName(String name);
}
