package com.anderl.domain

/**
 * Created by dasanderl on 14.09.14.
 */
class EntityProvider {

    static Entity getRandomEntity() {

        Entity entity = new Entity(
                name: "name_" + new Random().nextInt(12),
                age: new Random().nextInt(100),
                nestedEntitiesBatch10: [
                        new NestedEntity(
                                nestedName: "nestedName_" + new Random().nextInt(12),
                                nestedAge: new Random().nextInt(100),
                        ),
                        new NestedEntity(
                                nestedName: "nestedName_" + new Random().nextInt(12),
                                nestedAge: new Random().nextInt(100),
                        )
                ],
                nestedEntitiesNoBatch: [
                        new NestedEntity(
                                nestedName: "nestedName_" + new Random().nextInt(12),
                                nestedAge: new Random().nextInt(100),
                        ),
                        new NestedEntity(
                                nestedName: "nestedName_" + new Random().nextInt(12),
                                nestedAge: new Random().nextInt(100),
                        )
                ]);
//        entity.nestedEntitiesBatch10.each() {nested -> nested.entityBatch10 = entity }
//        entity.nestedEntitiesNoBatch.each() {nested -> nested.entityNoBatch = entity }
    }
}
