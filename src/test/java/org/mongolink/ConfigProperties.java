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

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mongodb.*;

import java.io.InputStream;
import java.util.*;

public class ConfigProperties {

    public Settings addSettings(Settings settings) {

        ArrayList<MongoCredential> credentials = Lists.newArrayList();
        if(!Strings.isNullOrEmpty(getDBUser())) {
            MongoCredential credential = MongoCredential.createCredential(getDBUser(), "test", getDBPassword().toCharArray());
            credentials.add(credential);
        }
        MongoClient mongoClient = new MongoClient(new ServerAddress(getDBHost(), getDBPort()), credentials);
        return settings.withClient(mongoClient).withDbName("test");
    }

    public String getDBHost() {
        return getProperty("db.host");
    }

    public String getDBName() {
        return getProperty("db.name");
    }

    public int getDBPort() {
        return Integer.valueOf(getProperty("db.port"));
    }

    public String getDBUser() {
        return getProperty("db.user");
    }

    public String getDBPassword() {
        return getProperty("db.password");
    }

    private String getProperty(String nom) {
        return Config.INSTANCE.properties.getProperty(nom);
    }

    private static enum Config {
        INSTANCE;

        private Config() {
            InputStream stream = getClass().getClassLoader().getResourceAsStream("conf.properties");
            properties = new Properties();
            try {
                properties.load(stream);
                stream.close();
            } catch (Exception e) {
                //
            }
        }

        private final Properties properties;
    }

}
