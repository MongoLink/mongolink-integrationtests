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

package org.mongolink.test.integrationMapping;

import org.mongolink.domain.mapper.AggregateMap;
import org.mongolink.domain.mapper.SubclassMap;
import org.mongolink.test.entity.FakeChildAggregate;
import org.mongolink.test.entity.FakeEntity;
import org.mongolink.test.entity.OtherFakeChildAggregate;


public class FakeAggregateWithTwoSubclassMapping extends AggregateMap<FakeEntity> {

    @Override
    public void map() {
        id().onProperty(e -> e.getId());
        property().onProperty(e -> e.getValue());
        property().onProperty(e -> e.getIndex());
        collection().onProperty(e -> e.getComments());
        property().onProperty(e -> e.getComment());
        subclass(new SubclassMap<FakeChildAggregate>() {

            @Override
            public void map() {
                property().onProperty(e -> e.getChildName());
            }
        });
        subclass(new SubclassMap<OtherFakeChildAggregate>() {
            @Override
            public void map() {

            }
        });
    }

}
