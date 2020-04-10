package com.ingrid.todolist.model;

import com.ingrid.todolist.contracts.ManageTodoContract;

public class ManageTodoPresenter implements ManageTodoContract.Presenter {
    private ManageTodoContract.View view;
    private TodoItem item;
    private TodoDatabase db;

    public ManageTodoPresenter(ManageTodoContract.View view, TodoDatabase db, TodoItem item) {
        this.view = view;
        this.item = item;
        this.db = db;

        if (item != null) {
            view.showEditMode(this.item);
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

        view.showAddSuccess();
        view.close();
    }

    private void editTodo(TodoItem item, String title, String description) {
        item.setTitle(title);
        item.setDescription(description);

        db.editItem(item);

        view.showEditSuccess();
        view.close();
    }
}
