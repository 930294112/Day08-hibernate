package com.lanou.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by dllo on 17/10/19.
 */
public class Item {
    private int id;
    private String name;
    private Set<Category> categorySet = new HashSet<>();

    public Set<Category> getCategorySet() {
        return categorySet;
    }

    public void setCategorySet(Set<Category> categorySet) {
        this.categorySet = categorySet;
    }

    public Item() {
    }

    public Item(int id, String name) {

        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

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

    public Item(String name) {

        this.name = name;
    }
}
