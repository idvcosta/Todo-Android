package com.ingrid.todolist.model;

import com.ingrid.todolist.activities.ListMode;
import com.ingrid.todolist.contracts.ListTodoContract;

import java.util.List;

public class TodoListPresenter implements ListTodoContract.Presenter {

    private ListMode listMode;
    private ListTodoContract.View view;
    private TodoDatabase db;

    public TodoListPresenter(ListTodoContract.View view, TodoDatabase db) {
        this.view = view;
        this.db = db;

        listMode = ListMode.LIST;
    }

    public void load() {
        List<TodoItem> items = db.allTodos();

        this.view.showList(items);
    }

    @Override
    public void onLongPress() {
        this.listMode = ListMode.SELECT;
        this.view.showSelectMode();
        this.view.invalidateOptionsMenu();
    }

    public boolean isSelectionMode() {
        return listMode == ListMode.SELECT;
    }

    @Override
    public void onBackPressed() {
        setListMode();
    }

    private void setListMode() {
        this.listMode = ListMode.LIST;
        this.view.showListMode();
        this.view.invalidateOptionsMenu();
    }

    public void delete(List<Long> selectedIds) {
        db.delete(selectedIds);
        setListMode();
        load();
    }
}
