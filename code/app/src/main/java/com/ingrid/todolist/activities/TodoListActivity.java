package com.ingrid.todolist.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ingrid.todolist.R;
import com.ingrid.todolist.activities.adapters.TodosAdapter;
import com.ingrid.todolist.contracts.ListTodoContract;
import com.ingrid.todolist.model.TodoDatabase;
import com.ingrid.todolist.model.TodoItem;
import com.ingrid.todolist.model.TodoListPresenter;

import java.util.List;

public class TodoListActivity extends AppCompatActivity implements ListTodoContract.View {

    private RecyclerView rvTodos;
    private TodosAdapter adapter;

    private ListTodoContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.load();
    }

    private void init() {
        FloatingActionButton btAdd = findViewById(R.id.button_add);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTodo();
            }
        });
        this.rvTodos = findViewById(R.id.rvToDos);
        this.rvTodos.setLayoutManager(new LinearLayoutManager(this));

        presenter = new TodoListPresenter(this, new TodoDatabase(this));
    }

    @Override
    public void onBackPressed() {
        presenter.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        presenter.onBackPressed();

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        int menuId;
        if (this.presenter.isSelectionMode()) {
            menuId = R.menu.list_menu_edit_todo;
        } else {
            menuId = R.menu.list_menu_sort;
        }
        getMenuInflater().inflate(menuId, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuTitleAsc:
                sortTitleAsc();
                return true;
            case R.id.menuTitleDesc:
                sortTitleDesc();
                return true;
            case R.id.menuPriorityHigh:
                sortPriorityHigh();
                return true;
            case R.id.menuPriorityLow:
                sortPriorityLow();
                return true;
            case R.id.menuDelete:
                deleteTodos();
                return true;
            case R.id.menuMark:
                markTodos();
                return true;
            case R.id.menuUnmark:
                unmarkTodos();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void sortTitleAsc() {
        presenter.sortTitleAsc();
    }

    private void sortTitleDesc() {
        presenter.sortTitleDesc();
    }

    private void sortPriorityHigh() {
        presenter.sortPriorityHigh();
    }


    private void sortPriorityLow() {
        presenter.sortPriorityLow();
    }

    private void deleteTodos() {
        List<Long> selectedIds = adapter.getSelectedIds();
        presenter.delete(selectedIds);
    }

    private void markTodos() {
        List<Long> selectedIds = adapter.getSelectedIds();
        presenter.mark(selectedIds);
    }

    private void unmarkTodos() {
        List<Long> selectedIds = adapter.getSelectedIds();
        presenter.unmark(selectedIds);
    }

    private void addTodo() {
        ManageTodoActivity.startActivityToCreate(this);
    }

    public void showList(List<TodoItem> items) {
        this.adapter = new TodosAdapter(this, items, presenter);
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
