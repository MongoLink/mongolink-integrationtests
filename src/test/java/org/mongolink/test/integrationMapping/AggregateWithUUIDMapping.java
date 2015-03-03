package org.mongolink.test.integrationMapping;

import org.mongolink.domain.mapper.AggregateMap;
import org.mongolink.test.entity.EntityWithUUID;

public class AggregateWithUUIDMapping extends AggregateMap<EntityWithUUID> {

    @Override
    public void map() {
        id().onProperty(e -> e.getId()).natural();
    }
}
