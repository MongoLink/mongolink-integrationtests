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

import com.mongodb.DB;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.mongolink.domain.UpdateStrategies;
import org.mongolink.domain.mapper.ContextBuilder;

public class TestsWithMongo {

    @BeforeClass
    public static void beforeClass() {
        ContextBuilder builder = new ContextBuilder("org.mongolink.test.integrationMapping");
        ConfigProperties config = new ConfigProperties();
        sessionManager = MongoSessionManager.create(builder,
                new ConfigProperties().addSettings(Settings.defaultInstance().withDefaultUpdateStrategy(UpdateStrategies.DIFF)));

        mongoSession = sessionManager.createSession();
        db = mongoSession.getDb();
    }

    @AfterClass
    public static void afterClass() {
        dropCollections();
        sessionManager.close();
    }

    @Before
    public void parentBefore() {
        mongoSession.start();
    }

    @After
    public void parentAfter() {
        mongoSession.stop();
    }

    private static void dropCollections() {
        for (String collection : db.getCollectionNames()) {
            if (!collection.startsWith("system.")) {
                db.getCollection(collection).drop();
            }
        }
    }


    protected static MongoSessionManager sessionManager;
    protected static MongoSession mongoSession;
    protected static DB db;

}
