package com.ingrid.todolist.contracts;

import com.ingrid.todolist.model.TodoItem;

import java.util.List;

public interface ListTodoContract {
    interface View{

        void showList(List<TodoItem> items);

        void showSelectMode();

        void invalidateOptionsMenu();

        void showListMode();
    }
    
    interface Presenter{
        void load();

        void onLongPress();

        void onBackPressed();

        boolean isSelectionMode();

        void delete(List<Long> selectedIds);
    }
}
