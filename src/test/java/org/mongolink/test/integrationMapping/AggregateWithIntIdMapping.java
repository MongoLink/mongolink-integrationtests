package org.mongolink.test.integrationMapping;

import org.mongolink.domain.mapper.AggregateMap;
import org.mongolink.test.entity.EntityWithIdInt;

public class AggregateWithIntIdMapping extends AggregateMap<EntityWithIdInt> {
    


    @Override
    public void map() {
        id().onField("id").natural();
    }
}
