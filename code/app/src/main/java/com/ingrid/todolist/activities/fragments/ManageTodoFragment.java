package com.ingrid.todolist.activities.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ingrid.todolist.R;
import com.ingrid.todolist.contracts.ManageTodoContract;
import com.ingrid.todolist.model.ErrorType;
import com.ingrid.todolist.model.ManageTodoPresenter;
import com.ingrid.todolist.model.Priority;
import com.ingrid.todolist.model.TodoDatabase;
import com.ingrid.todolist.model.TodoItem;
import com.ingrid.todolist.util.Util;

public class ManageTodoFragment extends Fragment implements ManageTodoContract.View {
    private TextView tvTitle;
    private EditText etTitle;
    private EditText etDescription;
    private RadioGroup rgPriority;
    private Button btAdd;

    private ManageTodoContract.Presenter presenter;
    private TodoItem item;

    public ManageTodoFragment() {

    }

    public ManageTodoFragment(TodoItem item) {
        this.item = item;
    }

    public static Fragment newInstance(TodoItem item) {
        return new ManageTodoFragment(item);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_manage_todo, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.tvTitle = view.findViewById(R.id.tvTitle);
        this.etTitle = view.findViewById(R.id.etTitle);
        this.etDescription = view.findViewById(R.id.etDescription);
        this.rgPriority = view.findViewById(R.id.rgPriority);
        this.btAdd = view.findViewById(R.id.btAdd);

        Util.showKeyboard(requireContext(), etTitle);
        btAdd.setOnClickListener(source -> {
            saveTodo();
        });

        presenter = new ManageTodoPresenter(this, new TodoDatabase(requireContext()), this.item);
    }

    private void saveTodo() {
        String title = etTitle.getText().toString();
        String description = etDescription.getText().toString();
        int priorityId = rgPriority.getCheckedRadioButtonId();
        Priority priority;

        if (priorityId == R.id.rbHighPriority) {
            priority = Priority.HIGH;
        } else if (priorityId == R.id.rbMediumPriority) {
            priority = Priority.MEDIUM;
        } else {
            priority = Priority.LOW;
        }

        presenter.saveTodo(title, description, priority);
    }


    public void showAddSuccess() {
        Toast.makeText(requireContext(), R.string.add_todo_success, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showEditMode(TodoItem item) {
        this.tvTitle.setText(R.string.tvEditTitle);
        this.etTitle.setText(item.getTitle());
        this.etDescription.setText((item.getDescription()));
        this.btAdd.setText(R.string.bt_edit);

        Priority priority = item.getPriority();
        int selectedPriorityViewId;

        switch (priority) {
            case LOW:
                selectedPriorityViewId = R.id.rbLowPriority;
                break;
            case MEDIUM:
                selectedPriorityViewId = R.id.rbMediumPriority;
                break;
            case HIGH:
                selectedPriorityViewId = R.id.rbHighPriority;
                break;
            default:
                throw new IllegalStateException("not implemented for: " + priority);
        }

        ((RadioButton) getActivity().findViewById(selectedPriorityViewId)).setChecked(true);
    }

    public void showEditSuccess() {
        Toast.makeText(requireContext(), R.string.add_todo_editSuccess, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void close() {
        getActivity().finish();
    }

    @Override
    public void showErrorMessage(ErrorType errorType) {
        int stringId = errorType.getResId();

        Toast.makeText(requireContext(), stringId, Toast.LENGTH_SHORT).show();
    }
}
