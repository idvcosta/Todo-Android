package com.ingrid.todolist.model;

import java.io.Serializable;

public class TodoItem implements Serializable {
    private Long id;
    private String title;
    private String description;
    private boolean marked;
    private Priority priority;

    public TodoItem(Long id, String title, String description, boolean marked, Priority priority) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.marked = marked;
        this.priority = priority;
    }

    public TodoItem(String title, String description, Priority priority) {
        this(null, title, description, false, priority);
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

    public Priority getPriority() {
        return priority;
    }
}
