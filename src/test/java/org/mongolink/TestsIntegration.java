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

import com.google.common.collect.Lists;
import com.mongodb.BasicDBList;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.*;
import org.mongolink.test.entity.*;

import static org.fest.assertions.Assertions.*;


@SuppressWarnings("unchecked")
public class TestsIntegration extends TestsWithMongo {

    private void initData() {
        Document fakeEntity = new Document();
        fakeEntity.put("_id", new ObjectId("4d9d9b5e36a9a4265ea9ecbe"));
        fakeEntity.put("value", "fake entity value");
        fakeEntity.put("comments", new BasicDBList());
        fakeEntity.put("index", 42);
        fakeEntity.put("comment", new Document("value", "the comment"));

        Document fakeChild = new Document();
        fakeChild.put("_id", new ObjectId("5d9d9b5e36a9a4265ea9ecbe"));
        fakeChild.put("childName", "child value");
        fakeChild.put("value", "parent value");
        fakeChild.put("comments", new BasicDBList());
        fakeChild.put("index", 0);
        fakeChild.put("__discriminator", "FakeChildAggregate");

        db.getCollection("fakeentity").insertMany(Lists.newArrayList(fakeEntity, fakeChild));

        Document naturalIdEntity = new Document();
        naturalIdEntity.put("_id", "naturalkey");
        naturalIdEntity.put("value", "naturalvalue");

        db.getCollection("fakeentitywithnaturalid").insertOne(naturalIdEntity);
    }

    @Before
    public void before() {
        initData();
    }

    @Test
    public void canGetById() {
        FakeEntity entityFound = mongoSession.get("4d9d9b5e36a9a4265ea9ecbe", FakeEntity.class);

        assertThat(entityFound).isNotNull();
        assertThat(entityFound.getId()).isEqualTo("4d9d9b5e36a9a4265ea9ecbe");
        assertThat(entityFound.getValue()).isEqualTo("fake entity value");
    }

    @Test
    public void canGetByNaturalId() {
        FakeEntityWithNaturalId fakeAggregateWithNaturalId = mongoSession.get("naturalkey",
                FakeEntityWithNaturalId.class);

        assertThat(fakeAggregateWithNaturalId).isNotNull();
        assertThat(fakeAggregateWithNaturalId.getNaturalKey()).isEqualTo("naturalkey");
    }

    @Test
    public void insertingSetId() {
        MongoCollection<Document> testid = db.getCollection("testid");
        Document dbo = new Document();

        testid.insertOne(dbo);

        assertThat(dbo.get("_id")).isNotNull();
    }

    @Test
    public void canGetChildEntity() {
        FakeChildAggregate entity = (FakeChildAggregate) mongoSession.get("5d9d9b5e36a9a4265ea9ecbe", FakeEntity.class);

        assertThat(entity).isNotNull();
        assertThat(entity.getValue()).isEqualTo("parent value");
        assertThat(entity.getChildName()).isEqualTo("child value");
    }

    @Test
    public void canSaveChildEntity() {
        FakeChildAggregate FakeChildAggregate = new FakeChildAggregate();
        FakeChildAggregate.setChildName("child");
        FakeChildAggregate.setValue("value from parent");
        FakeChildAggregate.addComment("this is a comment!");

        mongoSession.save(FakeChildAggregate);

        FakeChildAggregate entityFound = mongoSession.get(FakeChildAggregate.getId(), FakeChildAggregate.class);

        assertThat(entityFound).isNotNull();
        assertThat(entityFound.getComments()).hasSize(1);
    }

    @Test
    public void canPopulateComponent() {
        final FakeEntity entity = mongoSession.get("4d9d9b5e36a9a4265ea9ecbe", FakeEntity.class);

        assertThat(entity.getComment()).isNotNull();
        assertThat(entity.getComment().getValue()).isEqualTo("the comment");
    }

}
