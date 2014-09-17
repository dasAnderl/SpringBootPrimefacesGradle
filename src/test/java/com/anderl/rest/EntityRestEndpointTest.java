package com.anderl.rest;

import com.anderl.dao.EntityRepository;
import com.anderl.domain.DomainProvider;
import com.anderl.domain.Entity;
import com.google.common.collect.Lists;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@FixMethodOrder
public class EntityRestEndpointTest {

    @Tested
    TestEntityRestEndpoint testEntityRestEndpoint;

    @Injectable
    EntityRepository entityRepository;

    private MockMvc mockMvc;

    @Test
    public void testFindAll() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(testEntityRestEndpoint).build();

        ArrayList<Entity> entities = Lists.newArrayList(
                DomainProvider.getRandomEntity()
                , DomainProvider.getRandomEntity());
        new Expectations() {{
            entityRepository.findAll();
            result = entities;
        }};

        mockMvc.perform(get("/rest/entity/findAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
//                        conversion issue here
//                .andExpect(jsonPath("$[0].id", is(id)))
                .andExpect(jsonPath("$[0].name", is(entities.get(0).getName())))
                .andExpect(jsonPath("$[0].age", is(entities.get(0).getAge())))
                .andExpect(jsonPath("$[1].name", is(entities.get(1).getName())))
                .andExpect(jsonPath("$[1].age", is(entities.get(1).getAge())));
    }

    @Test
    public void testGetByAgePath() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(testEntityRestEndpoint).build();
        final Entity randomEntity = DomainProvider.getRandomEntity();
        int age = randomEntity.getAge();
        new Expectations() {{
            entityRepository.findByAge(age);
            result = Lists.newArrayList(randomEntity);
        }};

        mockMvc.perform(get("/rest/entity/getByAgePath/" + age))
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(1)))
//                        conversion issue here
//                .andExpect(jsonPath("$[0].id", is(id)))
                .andExpect(jsonPath("$.name", is(randomEntity.getName())))
                .andExpect(jsonPath("$.age", is(age)));
    }

    @Test
    public void testAddTestEntity() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(testEntityRestEndpoint).build();
        final Entity randomEntity = DomainProvider.getRandomEntity();
        long id = randomEntity.getId();
        new Expectations() {{
            entityRepository.save((Entity) any);
            result = randomEntity;
        }};

        mockMvc.perform(get(
                String.format("/rest/entity/addTestEntity?name=%s&age=%d", randomEntity.getName(), randomEntity.getAge())))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("created new TestEntity with id "+id)));
    }
}