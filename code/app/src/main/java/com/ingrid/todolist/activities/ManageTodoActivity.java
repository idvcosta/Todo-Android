package com.ingrid.todolist.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ingrid.todolist.R;
import com.ingrid.todolist.activities.fragments.ManageTodoFragment;
import com.ingrid.todolist.contracts.ManageTodoContract;
import com.ingrid.todolist.model.ErrorType;
import com.ingrid.todolist.model.ManageTodoPresenter;
import com.ingrid.todolist.model.Priority;
import com.ingrid.todolist.model.TodoDatabase;
import com.ingrid.todolist.model.TodoItem;
import com.ingrid.todolist.util.Util;

public class ManageTodoActivity extends AppCompatActivity {

    private static final String EXTRA_ITEM = "item";
    private ManageTodoFragment manageTodoFragment;

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
        setContentView(R.layout.activity_manage_todo);

        init(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        manageTodoFragment.onSaveInstanceState(outState);
    }

    private void init(Bundle savedInstanceState) {
        TodoItem item = (TodoItem) getIntent().getSerializableExtra(EXTRA_ITEM);
        if (savedInstanceState != null) {
            item = (TodoItem) savedInstanceState.getSerializable("todo");
        }

        this.manageTodoFragment = ManageTodoFragment.newInstance(item);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, manageTodoFragment);
        fragmentTransaction.commit();
    }

}
