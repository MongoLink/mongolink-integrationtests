package org.mongolink;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.mongolink.test.entity.Comment;
import org.mongolink.test.entity.FakeEntity;

import static org.fest.assertions.Assertions.assertThat;

public class TestsDiffStrategy extends TestsWithMongo {

    private void initData() {
        BasicDBObject fakeEntity = new BasicDBObject();
        fakeEntity.put("_id", new ObjectId("4d9d9b5e36a9a4265ea9ecbe"));
        fakeEntity.put("value", "fake entity value");
        BasicDBList comments = new BasicDBList();
        comments.add(new BasicDBObject("value", "test"));
        fakeEntity.put("comments", comments);
        fakeEntity.put("index", 42);
        fakeEntity.put("comment", new BasicDBObject("value", "the comment"));



        db.getCollection("fakeentity").insert(fakeEntity);
    }

    @Before
    public void before() {
        initData();
    }

    @Test
    public void canUpdateArray() {
        FakeEntity fakeEntity = mongoSession.get("4d9d9b5e36a9a4265ea9ecbe", FakeEntity.class);
        fakeEntity.getComments().set(0, new Comment("other value"));

        mongoSession.flush();
        mongoSession.clear();

        FakeEntity entityReloaded = mongoSession.get("4d9d9b5e36a9a4265ea9ecbe", FakeEntity.class);
        assertThat(entityReloaded.getComments().get(0).getValue()).isEqualTo("other value");
    }
}
