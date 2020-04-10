package com.ingrid.todolist.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ingrid.todolist.R;
import com.ingrid.todolist.contracts.ManageTodoContract;
import com.ingrid.todolist.model.ManageTodoPresenter;
import com.ingrid.todolist.model.TodoDatabase;
import com.ingrid.todolist.model.TodoItem;

public class ManageTodoActivity extends AppCompatActivity implements ManageTodoContract.View {

    private static final String EXTRA_ITEM = "item";

    private TextView tvTitle;
    private EditText etTitle;
    private EditText etDescription;
    private Button btAdd;

    private ManageTodoContract.Presenter presenter;

    public static void startActivityToCreate(Context context) {
        context.startActivity(new Intent(context, ManageTodoActivity.class));
    }

    public static void startActivityToEdit(Context context, TodoItem item) {
        Intent intent = new Intent(context, ManageTodoActivity.class);
        intent.putExtra(EXTRA_ITEM, item);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        init();
    }

    private void init() {
        TodoItem item = (TodoItem) getIntent().getSerializableExtra(EXTRA_ITEM);

        this.tvTitle = this.findViewById(R.id.tvTitle);
        this.etTitle = this.findViewById(R.id.etTitle);
        this.etDescription = this.findViewById(R.id.etDescription);

        this.btAdd = this.findViewById(R.id.btAdd);
        btAdd.setOnClickListener(view -> {
            String title = etTitle.getText().toString();
            String description = etDescription.getText().toString();

            presenter.saveTodo(title, description);
        });

        presenter = new ManageTodoPresenter(this, new TodoDatabase(this), item);
    }

    public void showAddSuccess() {
        Toast.makeText(this, R.string.add_todo_success, Toast.LENGTH_SHORT).show();
    }

    public void showEditMode(TodoItem item) {
        this.tvTitle.setText(R.string.tvEditTitle);
        this.etTitle.setText(item.getTitle());
        this.etDescription.setText((item.getDescription()));
        this.btAdd.setText(R.string.bt_edit);
    }

    public void showEditSuccess() {
        Toast.makeText(this, R.string.add_todo_editSuccess, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void close() {
        finish();
    }
}
