package org.mongolink.test.entity;

public class ChildComment extends Comment{

    public ChildComment() {

    }

    public ChildComment(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String name;
}
