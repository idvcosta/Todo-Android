package com.ingrid.todolist.model;

import com.ingrid.todolist.activities.AddTodoActivity;

public class AddTodoModel {
    private AddTodoActivity activity;
    private TodoItem item;
    private TodoDatabase db;

    public AddTodoModel(AddTodoActivity addTodoActivity, TodoItem item) {
        activity = addTodoActivity;
        this.item = item;
        db = new TodoDatabase(activity);

        if (item != null) {
            activity.showEditMode(this.item);
        }
    }

    public void saveTodo(String title, String description) {
        if (item == null) {
            addTodo(title, description);
        } else {
            editTodo(item, title, description);
        }
    }

    private void addTodo(String title, String description) {
        TodoItem todoItem = new TodoItem(null, title, description);
        db.saveTodo(todoItem);

        activity.showAddSuccess();
        activity.finish();
    }

    private void editTodo(TodoItem item, String title, String description) {
        item.setTitle(title);
        item.setDescription(description);

        db.editItem(item);

        activity.showEditSuccess();
        activity.finish();
    }
}
