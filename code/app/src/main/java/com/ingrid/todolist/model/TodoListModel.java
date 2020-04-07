package com.ingrid.todolist.model;

import com.ingrid.todolist.activities.ListMode;
import com.ingrid.todolist.activities.TodoListActivity;
import com.ingrid.todolist.activities.adapters.LongPressListener;

import java.util.List;

public class TodoListModel implements LongPressListener {

    private ListMode listMode;
    private TodoListActivity todoListActivity;
    private TodoDatabase db;

    public TodoListModel(TodoListActivity todoListActivity) {
        this.todoListActivity = todoListActivity;
        db = new TodoDatabase(todoListActivity);
        listMode = ListMode.LIST;
    }

    public void load() {
        List<TodoItem> items = db.allTodos();

        this.todoListActivity.showList(items);
    }

    @Override
    public void onLongPress() {
        this.listMode = ListMode.SELECT;
        this.todoListActivity.showSelectMode();
        this.todoListActivity.invalidateOptionsMenu();
    }

    public boolean isSelectionMode() {
        return listMode == ListMode.SELECT;
    }

    public void onBackPressed() {
        setListMode();
    }

    private void setListMode() {
        this.listMode = ListMode.LIST;
        this.todoListActivity.showListMode();
        this.todoListActivity.invalidateOptionsMenu();
    }

    public void delete(List<Long> selectedIds) {
        db.delete(selectedIds);
        setListMode();
        load();
    }
}
