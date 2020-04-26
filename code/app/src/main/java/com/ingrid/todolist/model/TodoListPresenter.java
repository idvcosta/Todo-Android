package com.ingrid.todolist.model;

import com.ingrid.todolist.activities.ListMode;
import com.ingrid.todolist.contracts.ListTodoContract;

import java.util.Collection;
import java.util.Comparator;
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
        List<TodoItem> items = db.allTodos(SortType.NONE);

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
        if (this.listMode == ListMode.LIST) {
            view.finish();
        } else {
            setListMode();
        }
    }

    private void setListMode() {
        this.listMode = ListMode.LIST;
        this.view.showListMode();
        this.view.invalidateOptionsMenu();
    }

    @Override
    public void sortTitleAsc() {
        List<TodoItem> items = db.allTodos(SortType.TITLE_ASC);
        this.view.showList(items);
    }

    @Override
    public void sortTitleDesc() {
        List<TodoItem> items = db.allTodos(SortType.TITLE_DESC);
        this.view.showList(items);
    }

    @Override
    public void sortPriorityHigh() {
        List<TodoItem> items = db.allTodos(SortType.PRIORITY_HIGH);
        this.view.showList(items);
    }

    @Override
    public void sortPriorityLow() {
        List<TodoItem> items = db.allTodos(SortType.PRIORITY_LOW);
        this.view.showList(items);
    }

    public void delete(List<Long> selectedIds) {
        db.delete(selectedIds);
        setListMode();
        load();
    }

    @Override
    public void mark(List<Long> selectedIds) {
        db.mark(selectedIds);
        setListMode();
        load();
    }

    @Override
    public void unmark(List<Long> selectedIds) {
        db.unmark(selectedIds);
        setListMode();
        load();
    }
}
