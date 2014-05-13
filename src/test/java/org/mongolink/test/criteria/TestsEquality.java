package org.mongolink.test.criteria;

import org.junit.Test;
import org.mongolink.TestsWithMongo;
import org.mongolink.domain.criteria.Criteria;
import org.mongolink.domain.criteria.Restrictions;
import org.mongolink.test.entity.FakeEntity;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsEquality extends TestsWithMongo {


    @Test
    public void canGetByEqCriteria() {
        givenAnEntity();
        final Criteria criteria = mongoSession.createCriteria(FakeEntity.class);
        criteria.add(Restrictions.equals("value", "fake entity value"));

        final List<FakeEntity> list = criteria.list();

        assertThat(list.size(), is(1));
    }

    private void givenAnEntity() {
        final FakeEntity entity = new FakeEntity("fake entity value");
        mongoSession.save(entity);
        mongoSession.flush();
    }
}
