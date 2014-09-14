package com.anderl.rest;

import com.anderl.dao.TestEntityRepository;
import com.anderl.domain.Entity;
import com.anderl.domain.EntityBuilder;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by dasanderl on 13.09.14.
 */
@Controller
@RequestMapping("/rest/testEntity")
public class TestEntityRestEndpoint {

    @Autowired
    private TestEntityRepository testEntityRepository;

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Entity> findAll() {
        return Lists.newArrayList(testEntityRepository.findAll());
    }

    @RequestMapping(value = "/getByAgePath/{age}", method = RequestMethod.GET)
    public ResponseEntity<Entity> getByAgePath(@PathVariable int age) {

        List<Entity> testEntities = testEntityRepository.findByAge(age);
        Entity entity = testEntities.isEmpty() ? null : testEntities.get(0);
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @RequestMapping(value = "/addTestEntity", method = RequestMethod.GET, params = {"name", "age"})
    public ResponseEntity<String> addTestEntity(@RequestParam String name, @RequestParam int age) {

        Entity entity = EntityBuilder.aTestEntity().withName(name).withAge(age).build();
        testEntityRepository.save(entity);
        return new ResponseEntity<>("created new TestEntity", HttpStatus.OK);
    }
}
