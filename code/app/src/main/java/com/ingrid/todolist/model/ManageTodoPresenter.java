package com.ingrid.todolist.model;

import com.ingrid.todolist.contracts.ManageTodoContract;
import com.ingrid.todolist.util.Util;

public class ManageTodoPresenter implements ManageTodoContract.Presenter {
    private static final int MAX_TITLE_SIZE = 15;
    private static final int MIN_TITLE_SIZE = 3;
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
        ErrorType errorType = null;

        if (!Util.isValid(title)) {
            errorType = ErrorType.EMPTY_TITLE;
        } else if (!Util.isValid(description)) {
            errorType = ErrorType.EMPTY_DESCRIPTION;
        } else if (title.length() > MAX_TITLE_SIZE) {
            errorType = ErrorType.LARGE_TITLE;
        } else if (title.length() < MIN_TITLE_SIZE) {
            errorType = ErrorType.SMALL_TITLE;
        }

        if (errorType == null) {
            if (item == null) {
                addTodo(title, description);
            } else {
                editTodo(item, title, description);
            }
        } else {
            view.showErrorMessage(errorType);
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
