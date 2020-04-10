package com.ingrid.todolist.contracts;

import com.ingrid.todolist.model.TodoItem;

public interface ManageTodoContract {
    interface View{
        void showEditMode(TodoItem item);

        void showAddSuccess();

        void close();

        void showEditSuccess();
    }
    
    interface Presenter{

        void saveTodo(String title, String description);
    }
}
