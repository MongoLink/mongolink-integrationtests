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
import org.mongolink.domain.criteria.Criteria;
import org.mongolink.domain.criteria.Restrictions;
import org.mongolink.test.entity.FakeEntity;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class TestsSearchIntegration extends TestsWithMongo {


    @Test
    public void canLimitSearch() {
        for (int i = 0; i < 10; i++) {
            mongoSession.save(new FakeEntity("valeur"));
        }
        final Criteria criteria = mongoSession.createCriteria(FakeEntity.class);
        criteria.add(Restrictions.equals("value", "valeur"));
        criteria.limit(1);

        final List result = criteria.list();

        assertThat(result).hasSize(1);
    }

    @Test
    public void canSkipSearch() {
        for (int i = 0; i < 10; i++) {
            mongoSession.save(new FakeEntity("valeur"));
        }
        final Criteria criteria = mongoSession.createCriteria(FakeEntity.class);
        criteria.add(Restrictions.equals("value", "valeur"));
        criteria.skip(1);

        final List result = criteria.list();

        assertThat(result).hasSize(9);
    }
}
