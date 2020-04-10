package com.ingrid.todolist.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    public void onBackPressed() {
        model.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        model.onBackPressed();

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(this.model.isSelectionMode()){
            getMenuInflater().inflate(R.menu.delete_menu, menu);
        }

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuDelete:
                deleteTodos();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deleteTodos() {
        List<Long> selectedIds = adapter.getSelectedIds();
        model.delete(selectedIds);
    }

    private void addTodo() {
        ManageTodoActivity.startActivityToCreate(this);
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

    public void showListMode() {
        this.adapter.setMode(ListMode.LIST);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
    }
}
