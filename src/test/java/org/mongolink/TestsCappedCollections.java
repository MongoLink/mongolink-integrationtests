package org.mongolink;

import org.bson.Document;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsCappedCollections extends TestsWithMongo {

    @Test
    public void cappedCollectionDropItems() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            Document naturalIdEntity = new Document();
            naturalIdEntity.put("_id", "cappedid" + i);
            naturalIdEntity.put("value", "cappedvalue" + i);
            db.getCollection("fakeentitywithcap").insertOne(naturalIdEntity);
        }

        assertThat(db.getCollection("fakeentitywithcap").count(), is(50L));
    }
}
