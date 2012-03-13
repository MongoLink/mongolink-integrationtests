package org.mongolink.test.integrationMapping;

import org.mongolink.domain.mapper.EntityMap;
import org.mongolink.test.entity.EntityWithIdInt;

public class EntityWithIntIdMapping extends EntityMap<EntityWithIdInt>{
    
    public EntityWithIntIdMapping() {
        super(EntityWithIdInt.class);
    }

    @Override
    protected void map() {
        id(element().getId()).natural();
    }
}
