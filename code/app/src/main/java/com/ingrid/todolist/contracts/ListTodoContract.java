package com.ingrid.todolist.contracts;

import com.ingrid.todolist.model.TodoItem;

import java.util.List;

public interface ListTodoContract {
    interface View{

        void showList(List<TodoItem> items);

        void showSelectMode();

        void invalidateOptionsMenu();

        void showListMode();

        void finish();
    }
    
    interface Presenter{
        void load();

        void onLongPress();

        void onBackPressed();

        boolean isSelectionMode();

        void sortTitleAsc();

        void sortTitleDesc();

        void sortPriorityHigh();

        void sortPriorityLow();

        void delete(List<Long> selectedIds);

        void mark(List<Long> selectedIds);

        void unmark(List<Long> selectedIds);
    }
}
