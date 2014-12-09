package com.anderl.search

import com.anderl.DomainTestApplication
import com.anderl.dao.EntityRepository
import com.anderl.domain.DomainProvider
import com.anderl.domain.Entity
import org.hamcrest.Matchers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

/**
 * Created by dasanderl on 09.12.14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DomainTestApplication.class)
class SearchTest {

    @Autowired
    private EntityRepository entityRepository

    @Autowired
    private PagingService<Entity> pagingService

    @Before
    def void createEntities() {
        def ent1 = DomainProvider.getRandomEntity()
        def ent2 = DomainProvider.getRandomEntity()
        def ent3 = DomainProvider.getRandomEntity()
        entityRepository.save([ent1, ent2, ent3])
    }

    @Test
    def void testEntityFilter() {
        EntityFilter entityFilter = new EntityFilter()
        Assert.assertThat("must be 3", pagingService.count(entityFilter), Matchers.is(3));
    }
}
