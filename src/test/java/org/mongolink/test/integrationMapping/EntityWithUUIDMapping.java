package org.mongolink.test.integrationMapping;

import org.mongolink.domain.mapper.EntityMap;
import org.mongolink.test.entity.EntityWithUUID;

public class EntityWithUUIDMapping extends EntityMap<EntityWithUUID>{

    public EntityWithUUIDMapping() {
        super(EntityWithUUID.class);
    }

    @Override
    protected void map() {
        id(element().getId()).natural();
    }
}
