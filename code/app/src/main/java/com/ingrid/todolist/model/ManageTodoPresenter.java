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
            if(item.getId()!=null){
                view.showEditMode(this.item);
            }else{
                view.showItem(this.item);
            }
        }
    }

    public void saveTodo(TodoItem todo) {
        ErrorType errorType = null;

        if (!Util.isValid(todo.getTitle())) {
            errorType = ErrorType.EMPTY_TITLE;
        } else if (!Util.isValid(todo.getDescription())) {
            errorType = ErrorType.EMPTY_DESCRIPTION;
        } else if (todo.getTitle().length() > MAX_TITLE_SIZE) {
            errorType = ErrorType.LARGE_TITLE;
        } else if (todo.getTitle().length() < MIN_TITLE_SIZE) {
            errorType = ErrorType.SMALL_TITLE;
        }

        if (errorType == null) {
            boolean isNewItem = item == null || item.getId() == null;

            if (isNewItem) {
                addTodo(todo.getTitle(), todo.getDescription(), todo.getPriority());
            } else {
                editTodo(item, todo.getTitle(), todo.getDescription());
            }
        } else {
            view.showErrorMessage(errorType);
        }
    }


    private void addTodo(String title, String description, Priority priority) {
        TodoItem todoItem = new TodoItem(title, description, priority);
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
