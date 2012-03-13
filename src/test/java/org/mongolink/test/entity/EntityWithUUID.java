package org.mongolink.test.entity;

import java.util.UUID;

public class EntityWithUUID {

    public EntityWithUUID() {

    }

    public UUID getId() {
        return id;
    }

    private UUID id = UUID.randomUUID();
}
