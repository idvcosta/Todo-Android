package com.ingrid.todolist.model;

import com.ingrid.todolist.activities.TodoListActivity;

import java.util.List;

public class TodoListModel {
    private TodoListActivity todoListActivity;
    private TodoDatabase db;

    public TodoListModel(TodoListActivity todoListActivity) {
        this.todoListActivity = todoListActivity;
        db = new TodoDatabase(todoListActivity);
    }

    public void load() {
        List<TodoItem> items = db.allTodos();

        this.todoListActivity.showList(items);
    }
}
