package com.ingrid.todolist.model;

import java.io.Serializable;

public class TodoItem implements Serializable {
    private Long id;
    private String title;
    private String description;
    private boolean marked;

    public TodoItem(Long id, String title, String description, boolean marked) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.marked = marked;
    }

    public TodoItem(String title, String description) {
        this(null, title, description, false);
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isMarked() {
        return marked;
    }
}
