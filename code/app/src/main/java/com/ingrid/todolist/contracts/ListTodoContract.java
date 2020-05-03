package com.ingrid.todolist.contracts;

import com.ingrid.todolist.model.TodoItem;

import java.util.List;

public interface ListTodoContract {
    interface View {

        void showList(List<TodoItem> items);

        void showListMode();

        void showSelectMode();

        void invalidateOptionsMenu();

        void finish();

        void openTodoLandscape(TodoItem item);

        void openTodoPortrait(TodoItem item);
    }

    interface Presenter {
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

        void openTodo(TodoItem item);
    }
}
