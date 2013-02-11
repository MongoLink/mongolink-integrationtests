package org.mongolink;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.mongolink.test.entity.Comment;
import org.mongolink.test.entity.FakeEntity;

import static org.fest.assertions.Assertions.*;

public class TestsDiffStrategy extends TestsWithMongo {

    @Before
    public void before() {
        initData();
    }

    private void initData() {
        BasicDBObject fakeEntity = new BasicDBObject();
        fakeEntity.put("_id", new ObjectId("4d9d9b5e36a9a4265ea9ecbe"));
        fakeEntity.put("value", "fake entity value");
        BasicDBList comments = new BasicDBList();
        comments.add(new BasicDBObject("value", "test0"));
        comments.add(new BasicDBObject("value", "test1"));
        comments.add(new BasicDBObject("value", "test2"));
        comments.add(new BasicDBObject("value", "test3"));
        fakeEntity.put("comments", comments);
        fakeEntity.put("index", 42);
        fakeEntity.put("comment", new BasicDBObject("value", "the comment"));


        db.getCollection("fakeentity").insert(fakeEntity);
    }

    @Test
    public void canSetValue() {
        FakeEntity fakeEntity = mongoSession.get("4d9d9b5e36a9a4265ea9ecbe", FakeEntity.class);
        fakeEntity.getComments().set(0, new Comment("other value"));

        mongoSession.flush();
        mongoSession.clear();

        FakeEntity entityReloaded = mongoSession.get("4d9d9b5e36a9a4265ea9ecbe", FakeEntity.class);
        assertThat(entityReloaded.getComments()).hasSize(4);
        assertThat(entityReloaded.getComments().get(0).getValue()).isEqualTo("other value");
    }

    @Test
    public void canRemoveElementAtArbitraryIndex() {
        FakeEntity fakeEntity = mongoSession.get("4d9d9b5e36a9a4265ea9ecbe", FakeEntity.class);
        fakeEntity.getComments().set(0, new Comment("other value"));
        fakeEntity.getComments().remove(2);

        mongoSession.flush();
        mongoSession.clear();

        FakeEntity entityReloaded = mongoSession.get("4d9d9b5e36a9a4265ea9ecbe", FakeEntity.class);
        assertThat(entityReloaded.getComments()).hasSize(3);
        assertThat(entityReloaded.getComments()).onProperty("value").contains("other value", "test1", "test3");
    }

    @Test
    public void canAddMultipleElements() {
        FakeEntity fakeEntity = mongoSession.get("4d9d9b5e36a9a4265ea9ecbe", FakeEntity.class);
        fakeEntity.getComments().add(new Comment("test4"));
        fakeEntity.getComments().add(new Comment("test5"));

        mongoSession.flush();
        mongoSession.clear();

        FakeEntity entityReloaded = mongoSession.get("4d9d9b5e36a9a4265ea9ecbe", FakeEntity.class);
        assertThat(entityReloaded.getComments()).hasSize(6);
        assertThat(entityReloaded.getComments()).onProperty("value").contains("test0", "test1", "test2", "test3", "test4", "test5");
    }

    @Test
    public void canAddOneElement() {
        FakeEntity fakeEntity = mongoSession.get("4d9d9b5e36a9a4265ea9ecbe", FakeEntity.class);
        fakeEntity.getComments().add(new Comment("test4"));

        mongoSession.flush();
        mongoSession.clear();

        FakeEntity entityReloaded = mongoSession.get("4d9d9b5e36a9a4265ea9ecbe", FakeEntity.class);
        assertThat(entityReloaded.getComments()).hasSize(5);
        assertThat(entityReloaded.getComments()).onProperty("value").contains("test0", "test1", "test2", "test3", "test4");

    }
}
