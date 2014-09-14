package com.anderl.rest;

import com.anderl.dao.TestEntityRepository;
import com.anderl.domain.EntityBuilder;
import com.google.common.collect.Lists;
import mockit.Injectable;
import mockit.NonStrictExpectations;
import mockit.Tested;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FixMethodOrder
public class EntityRestEndpointTest {

    @Tested
    TestEntityRestEndpoint testEntityRestEndpoint;

    @Injectable
    TestEntityRepository testEntityRepository;

    private MockMvc mockMvc;

    @Test
    public void testFindAll() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(testEntityRestEndpoint).build();

        long id = 9;
        int age = 176765;
        String name = "some name";
        new NonStrictExpectations() {{
            testEntityRepository.findAll();
            result = Lists.newArrayList(
                    EntityBuilder.aTestEntity()
                            .withId(id)
                            .withAge(age)
                            .withName(name)
                            .build());
        }};

        mockMvc.perform(get("/rest/testEntity/findAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[0].id", is(id)))
                .andExpect(jsonPath("$[0].name", is(name)))
                .andExpect(jsonPath("$[0].age", is(age)));
    }

    @Test
    public void testGetByAgePath() throws Exception {

    }

    @Test
    public void testAddTestEntity() throws Exception {

    }
}