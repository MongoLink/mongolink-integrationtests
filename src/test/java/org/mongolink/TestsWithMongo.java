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

import com.mongodb.client.MongoDatabase;
import org.junit.*;
import org.mongolink.domain.mapper.ContextBuilder;
import org.mongolink.domain.session.MongoSessionImpl;

public class TestsWithMongo {

    @BeforeClass
    public static void beforeClass() {
        ContextBuilder builder = new ContextBuilder("org.mongolink.test.integrationMapping");
        sessionManager = MongoSessionManager.create(builder,
                new ConfigProperties().addSettings(Settings.defaultInstance().withDefaultUpdateStrategy(UpdateStrategies.DIFF)));

        mongoSession = (MongoSessionImpl) sessionManager.createSession();
    }

    @AfterClass
    public static void afterClass() {
        sessionManager.close();
    }

    @Before
    public void parentBefore() {
        mongoSession = (MongoSessionImpl) sessionManager.createSession();
        mongoSession.start();
        db = mongoSession.getDb();
    }

    @After
    public void parentAfter() {
        dropCollections();
        mongoSession.stop();
    }

    private static void dropCollections() {
        for (String collection : db.listCollectionNames()) {
            if (!collection.startsWith("system.")) {
                db.getCollection(collection).drop();
            }
        }
    }


    protected static MongoSessionManager sessionManager;
    protected static MongoSessionImpl mongoSession;
    protected static MongoDatabase db;

}
