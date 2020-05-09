package com.ingrid.todolist.contracts;

import com.ingrid.todolist.model.ErrorType;
import com.ingrid.todolist.model.Priority;
import com.ingrid.todolist.model.TodoItem;

public interface ManageTodoContract {
    interface View{
        void showEditMode(TodoItem item);

        void showAddSuccess();
        void showEditSuccess();

        void close();

        void showErrorMessage(ErrorType errorType);

        void showItem(TodoItem item);
    }
    
    interface Presenter{
        void saveTodo(TodoItem todo);

    }
}
