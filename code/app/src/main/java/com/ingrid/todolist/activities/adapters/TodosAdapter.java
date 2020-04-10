package com.ingrid.todolist.activities.adapters;

import android.content.Context;
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
import com.ingrid.todolist.model.TodoItem;

import java.util.ArrayList;
import java.util.List;

public class TodosAdapter extends RecyclerView.Adapter<TodosAdapter.ToDoHolder> {

    private Context context;
    private LongPressListener longPressListener;

    private List<TodoItem> items;
    private ListMode listMode;
    private List<Long> selectedIds;

    public TodosAdapter(Context context, List<TodoItem> items, LongPressListener longPressListener) {
        this.context = context;
        this.items = items;
        this.longPressListener = longPressListener;

        this.listMode = ListMode.LIST;
        this.selectedIds = new ArrayList<Long>();
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
        Long id = item.getId();

        holder.tvTitle.setText(title);
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
                    TodosAdapter.this.longPressListener.onLongPress();

                    return true;
                }
            });
        }
    }
}