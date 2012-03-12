/*
 * MongoLink, Object Document Mapper for Java and MongoDB
 *
 * Copyright (c) 2012, Arpinum or third-party contributors as
 * indicated by the @author tags
 *
 * MongoLink is free software: you can redistribute it and/or modify
 * it under the terms of the Lesser GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MongoLink is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Lesser GNU General Public License for more details.
 *
 * You should have received a copy of the Lesser GNU General Public License
 * along with MongoLink.  If not, see <http://www.gnu.org/licenses/>. 
 *
 */

package org.mongolink;


import com.mongodb.BasicDBObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mongolink.test.entity.FakeChildEntity;
import org.mongolink.test.entity.FakeEntity;
import org.mongolink.test.entity.OtherFakeChildEntity;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestsInheritanceIntegration extends TestsWithMongo {

    @Before
    public void before() {
        mongoSession.start();
    }

    @After
    public void after() {
        mongoSession.stop();
        db.getCollection("fakeentity").remove(new BasicDBObject());
    }

    @Test
    public void canGetAllEntityWithInheritance() {
        mongoSession.save(new FakeChildEntity());
        mongoSession.save(new OtherFakeChildEntity());
        mongoSession.clear();

        List<FakeEntity> fakeEntities = mongoSession.getAll(FakeEntity.class);

        assertThat(fakeEntities.size(), is(2));
    }

    @Test
    @Ignore
    public void canGetOnlyChildTypeOnGetAll() {
        mongoSession.save(new FakeChildEntity());
        mongoSession.save(new OtherFakeChildEntity());
        mongoSession.clear();

        List<FakeChildEntity> fakeEntities = mongoSession.getAll(FakeChildEntity.class);

        assertThat(fakeEntities.size(), is(1));
    }
}
