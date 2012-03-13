package org.mongolink.test.entity;

public class EntityWithIdInt {

    public EntityWithIdInt() {
    }

    public EntityWithIdInt(final int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    private int id = 0;
}
