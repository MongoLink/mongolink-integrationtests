package org.mongolink.test.integrationMapping;

import org.mongolink.domain.mapper.AggregateMap;
import org.mongolink.test.entity.EntityWithIdInt;

public class AggregateWithIntIdMapping extends AggregateMap<EntityWithIdInt> {
    
    public AggregateWithIntIdMapping() {
        super(EntityWithIdInt.class);
    }

    @Override
    protected void map() {
        id(element().getId()).natural();
    }
}
