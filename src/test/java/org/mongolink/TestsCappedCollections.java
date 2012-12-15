package org.mongolink;

import com.mongodb.BasicDBObject;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TestsCappedCollections extends TestsWithMongo {

    @Test
    public void cappedCollectionDropItems() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            BasicDBObject naturalIdEntity = new BasicDBObject();
            naturalIdEntity.put("_id", "cappedid" + i);
            naturalIdEntity.put("value", "cappedvalue" + i);
            db.getCollection("fakeentitywithcap").insert(naturalIdEntity);
        }

        assertThat(db.getCollection("fakeentitywithcap").isCapped(), is(true));
        assertThat(db.getCollection("fakeentitywithcap").count(), is(50L));
    }
}
