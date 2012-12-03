package org.mongolink.test.criteria;

import org.junit.Test;
import org.mongolink.TestsWithMongo;
import org.mongolink.domain.criteria.Criteria;
import org.mongolink.domain.criteria.Restrictions;
import org.mongolink.test.entity.FakeEntity;

import java.util.List;

import static org.fest.assertions.Assertions.*;

public class TestsElemMatch extends TestsWithMongo {



    @Test
    public void canFilterWithElemMatch() {
        anEntityWithComment("a comment");
        anEntityWithComment("no comment");
        final Criteria criteria = mongoSession.createCriteria(FakeEntity.class);
        criteria.add(Restrictions.elementMatch("comments").equals("value", "a comment"));

        final List<FakeEntity> list = criteria.list();

        assertThat(list).hasSize(1);
    }

    private void anEntityWithComment(String comment) {
        final FakeEntity entity = new FakeEntity("value");
        entity.addComment(comment);
        mongoSession.save(entity);
    }
}
