package org.mongolink;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mongolink.test.entity.EntityWithIdInt;
import org.mongolink.test.entity.EntityWithUUID;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsIdManagement extends TestsWithMongo {

    @Before
    public void before() {
        mongoSession.start();
    }

    @After
    public void after() {
        mongoSession.stop();
    }

    @Test
    public void canUseUUID() {
        final EntityWithUUID entity = new EntityWithUUID();
        mongoSession.save(entity);
        mongoSession.save(new EntityWithUUID());
        mongoSession.clear();

        final EntityWithUUID entityFound = mongoSession.get(entity.getId(), EntityWithUUID.class);

        assertThat(entityFound, notNullValue());
        assertThat(entityFound.getId(), is(entity.getId()));
    }

    @Test
    public void canUseInt() {
        final EntityWithIdInt entity = new EntityWithIdInt(3);
        mongoSession.save(entity);
        mongoSession.save(new EntityWithIdInt(2));
        mongoSession.clear();

        final EntityWithIdInt entityFound = mongoSession.get(3, EntityWithIdInt.class);
        assertThat(entityFound.getId(), is(3));
    }
}
