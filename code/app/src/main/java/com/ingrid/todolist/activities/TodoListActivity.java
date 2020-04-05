package com.ingrid.todolist.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ingrid.todolist.R;
import com.ingrid.todolist.activities.adapters.TodosAdapter;
import com.ingrid.todolist.model.TodoItem;
import com.ingrid.todolist.model.TodoListModel;

import java.util.List;

public class TodoListActivity extends AppCompatActivity {
    
    private TodoListModel model;
    private RecyclerView rvTodos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        model.load();
    }

    private void init() {
        Button btAdd = findViewById(R.id.button_add);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTodo();
            }
        });
        this.rvTodos = findViewById(R.id.rvToDos);
        this.rvTodos.setLayoutManager(new LinearLayoutManager(this));
//        this.rvTodos.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener(){
//
//            @Override
//            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent event) {
//
//                GestureDetector gestureDetector = new GestureDetector(TodoListActivity.this, new GestureDetector.SimpleOnGestureListener() {
//                    @Override
//                    public boolean onSingleTapUp(MotionEvent e) {
//                        return true;
//                    }
//
//                    @Override
//                    public void onLongPress(MotionEvent e) {
//                        Toast.makeText(TodoListActivity.this, "longPress",Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//                gestureDetector.onTouchEvent(event);
//
//                return false;
//            }
//        });

        model = new TodoListModel(this);
    }

    private void addTodo() {
        AddTodoActivity.startActivity(this);
    }

    public void showList(List<TodoItem> items) {
        rvTodos.setAdapter(new TodosAdapter(this,items));
    }
}
