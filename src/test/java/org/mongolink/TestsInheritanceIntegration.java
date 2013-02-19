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


import org.junit.After;
import org.junit.Test;
import org.mongolink.test.entity.ChildComment;
import org.mongolink.test.entity.FakeChildAggregate;
import org.mongolink.test.entity.FakeEntity;
import org.mongolink.test.entity.OtherFakeChildAggregate;

import java.util.List;

import static org.fest.assertions.Assertions.*;

public class TestsInheritanceIntegration extends TestsWithMongo {

    @After
    public void after() {
        db.getCollection("fakeentity").drop();
    }


    @Test
    public void canGetAllEntityWithInheritance() {
        mongoSession.save(new FakeChildAggregate());
        mongoSession.save(new OtherFakeChildAggregate());
        mongoSession.clear();

        List<FakeEntity> fakeEntities = mongoSession.getAll(FakeEntity.class);

        assertThat(fakeEntities).hasSize(2);
    }

    @Test
    public void canGetOnlyChildTypeOnGetAll() {
        mongoSession.save(new FakeChildAggregate());
        mongoSession.save(new OtherFakeChildAggregate());
        mongoSession.clear();

        List<FakeChildAggregate> fakeEntities = mongoSession.getAll(FakeChildAggregate.class);

        assertThat(fakeEntities).hasSize(1);
    }

    @Test
    public void canDealWithComponentInheritance() {
        final FakeEntity fakeAggregate = new FakeEntity("value");
        fakeAggregate.addComment(new ChildComment("jb"));
        mongoSession.save(fakeAggregate);

        final List<FakeEntity> fakeEntities = mongoSession.getAll(FakeEntity.class);

        assertThat(fakeEntities).hasSize(1);
        assertThat(fakeEntities.get(0).getComments()).hasSize(1);
        assertThat(fakeEntities.get(0).getComments().get(0)).isInstanceOf(ChildComment.class);
    }
}
