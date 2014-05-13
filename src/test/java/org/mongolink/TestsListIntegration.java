/*
 * MongoLink, Object Document Mapper for Java and MongoDB
 *
 * Copyright (c) 2012, Arpinum or third-party contributors as
 * indicated by the @author tags
 *
 * MongoLink is free software: you can redistribute it and/or modify
 * it under the terms of the Lesser GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or any later version.
 *
 * MongoLink is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Lesser GNU General Public License for more details.
 *
 * You should have received a copy of the Lesser GNU General Public License
 * along with MongoLink.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.mongolink;

import org.junit.Test;
import org.mongolink.test.entity.FakeChildAggregate;
import org.mongolink.test.entity.FakeEntity;

import static org.fest.assertions.Assertions.assertThat;

public class TestsListIntegration extends TestsWithMongo {

    @Test
    public void canRemoveAnElementFromList() {
        FakeEntity fake = new FakeEntity("test");
        fake.addComment("a comment");
        fake.addComment("another comment");
        mongoSession.save(fake);
        mongoSession.flush();

        fake.getComments().remove(1);
        mongoSession.stop();

        mongoSession = (org.mongolink.domain.session.MongoSessionImpl) sessionManager.createSession();
        mongoSession.start();
        final FakeEntity entityFound = mongoSession.get(fake.getId(), FakeEntity.class);
        assertThat(entityFound.getComments()).hasSize(1);
        assertThat(entityFound.getComments().get(0).getValue()).isEqualTo("a comment");
    }

    @Test
    public void canSaveComponentsInListWithoutDuplicateThemInChildClass() {
        FakeEntity fake = new FakeChildAggregate();
        fake.addComment("a comment");
        mongoSession.save(fake);
        mongoSession.flush();

        mongoSession = (org.mongolink.domain.session.MongoSessionImpl) sessionManager.createSession();
        mongoSession.start();
        FakeEntity entityFound = mongoSession.get(fake.getId(), FakeEntity.class);
        assertThat(entityFound.getComments()).hasSize(1);
    }
}
