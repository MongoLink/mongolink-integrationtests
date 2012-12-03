package org.mongolink.test.criteria;

import org.junit.Test;
import org.mongolink.TestsWithMongo;
import org.mongolink.domain.criteria.Criteria;
import org.mongolink.domain.criteria.Restrictions;
import org.mongolink.test.entity.FakeEntity;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsBetween extends TestsWithMongo {

    @Test
    public void canGetByBetweenCriteria() {
        givenAFakeEntityWithIndex(43);
        final Criteria criteria = mongoSession.createCriteria(FakeEntity.class);
        criteria.add(Restrictions.between("index", 42, 44));

        final List<FakeEntity> list = criteria.list();

        assertThat(list.size(), is(1));
    }


    @Test
    public void canGetByBetweenCriteriaWithBadBoundaries() {
        givenAFakeEntityWithIndex(43);
        final Criteria criteria = mongoSession.createCriteria(FakeEntity.class);
        criteria.add(Restrictions.between("index", 40, 42));

        final List<FakeEntity> list = criteria.list();

        assertThat(list.size(), is(0));
    }

    private void givenAFakeEntityWithIndex(final int index) {
        final FakeEntity value = new FakeEntity("value");
        value.setIndex(index);
        mongoSession.save(value);
    }
}
