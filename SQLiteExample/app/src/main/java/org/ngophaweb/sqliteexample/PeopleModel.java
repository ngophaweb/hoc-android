package org.ngophaweb.sqliteexample;

import java.io.Serializable;

/**
 * Created by Administrator on 26/04/2016.
 */
public class PeopleModel implements Serializable{
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.id + " - " + this.name;
    }
}
