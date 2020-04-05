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

    private RecyclerView rvTodos;
    private TodosAdapter adapter;

    private TodoListModel model;

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

        model = new TodoListModel(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        this.adapter.setMode(ListMode.LIST);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        return super.onSupportNavigateUp();

    }

    private void addTodo() {
        AddTodoActivity.startActivity(this);
    }

    public void showList(List<TodoItem> items) {
        this.adapter = new TodosAdapter(this, items, model);
        rvTodos.setAdapter(adapter);
    }

    public void showSelectMode() {
        this.adapter.setMode(ListMode.SELECT);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}
