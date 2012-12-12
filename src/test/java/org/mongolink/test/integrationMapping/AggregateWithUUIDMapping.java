package org.mongolink.test.integrationMapping;

import org.mongolink.domain.mapper.AggregateMap;
import org.mongolink.test.entity.EntityWithUUID;

public class AggregateWithUUIDMapping extends AggregateMap<EntityWithUUID> {

    public AggregateWithUUIDMapping() {
        super(EntityWithUUID.class);
    }

    @Override
    protected void map() {
        id(element().getId()).natural();
    }
}
