package com.ingrid.todolist.activities.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ingrid.todolist.R;
import com.ingrid.todolist.activities.ManageTodoActivity;
import com.ingrid.todolist.activities.ListMode;
import com.ingrid.todolist.contracts.ListTodoContract;
import com.ingrid.todolist.model.Priority;
import com.ingrid.todolist.model.TodoItem;

import java.util.ArrayList;
import java.util.List;

public class TodosAdapter extends RecyclerView.Adapter<TodosAdapter.ToDoHolder> {

    private static final int REGULAR_TEXT_FLAG = 0;
    private Context context;
    private ListTodoContract.Presenter presenter;

    private List<TodoItem> items;
    private ListMode listMode;
    private List<Long> selectedIds;

    private int lowColor;
    private int mediumColor;
    private int highColor;

    public TodosAdapter(Context context, List<TodoItem> items, ListTodoContract.Presenter presenter) {
        this.context = context;
        this.items = items;
        this.presenter = presenter;

        this.listMode = ListMode.LIST;
        this.selectedIds = new ArrayList<Long>();

        this.lowColor = context.getColor(R.color.lowPriority);
        this.mediumColor = context.getColor(R.color.mediumPriority);
        this.highColor = context.getColor(R.color.highPriority);
    }

    @NonNull
    @Override
    public ToDoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.todo_item, parent, false);
        final ToDoHolder toDoHolder = new ToDoHolder(view);

        view.setOnClickListener(view1 -> {
            TodoItem item = (TodoItem) view1.getTag();

            if (listMode == ListMode.LIST) {
                ManageTodoActivity.startActivityToEdit(context, item);
            } else {
                boolean currentSelection = toDoHolder.chSelection.isChecked();
                boolean nextSelection = !currentSelection;
                long todoId = item.getId();
                toDoHolder.chSelection.setChecked(nextSelection);

                if (nextSelection) {
                    selectedIds.add(todoId);
                } else {
                    selectedIds.remove(todoId);
                }
            }
        });

        return toDoHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoHolder holder, int position) {
        //pegar o título a partir da posição
        TodoItem item = this.items.get(position);
        String title = item.getTitle();
        boolean marked = item.isMarked();
        Long id = item.getId();
        Priority priority = item.getPriority();
        int priorityColor;

        switch (priority) {
            case LOW:
                priorityColor = lowColor;
                break;
            case MEDIUM:
                priorityColor = mediumColor;
                break;
            case HIGH:
                priorityColor = highColor;
                break;
            default:
                throw new IllegalStateException("not implemented for: " + priority);
        }

        holder.tvTitle.setText(title);
        holder.tvTitle.setTextColor(priorityColor);

        if (marked) {
            holder.tvTitle.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.tvTitle.setPaintFlags(REGULAR_TEXT_FLAG);
        }

        if (listMode == ListMode.LIST) {
            holder.chSelection.setVisibility(View.INVISIBLE);
        } else {
            boolean isTodoSelected = selectedIds.contains(id);

            holder.chSelection.setVisibility(View.VISIBLE);
            holder.chSelection.setChecked(isTodoSelected);
        }
        holder.view.setTag(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setMode(ListMode listMode) {
        this.selectedIds.clear();
        this.listMode = listMode;
        notifyDataSetChanged();
    }

    public List<Long> getSelectedIds() {
        return selectedIds;
    }

    class ToDoHolder extends RecyclerView.ViewHolder {
        private final View view;
        private final TextView tvTitle;
        private final CheckBox chSelection;

        public ToDoHolder(View view) {
            super(view);
            this.view = view;
            this.tvTitle = view.findViewById(R.id.tvTitle);
            this.chSelection = view.findViewById(R.id.cbSelection);

            view.setLongClickable(true);
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    TodosAdapter.this.presenter.onLongPress();

                    return true;
                }
            });
        }
    }
}