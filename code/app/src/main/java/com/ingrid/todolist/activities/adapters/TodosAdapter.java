package com.ingrid.todolist.activities.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ingrid.todolist.R;
import com.ingrid.todolist.activities.AddTodoActivity;
import com.ingrid.todolist.activities.ListMode;
import com.ingrid.todolist.model.TodoItem;

import java.util.List;

public class TodosAdapter extends RecyclerView.Adapter<TodosAdapter.ToDoHolder> {
    private Context context;
    private List<TodoItem> items;
    private LongPressListener longPressListener;
    private ListMode listMode;

    public TodosAdapter(Context context, List<TodoItem> items, LongPressListener longPressListener) {
        this.context = context;
        this.items = items;
        this.longPressListener = longPressListener;

        this.listMode = ListMode.LIST;
    }

    @NonNull
    @Override
    public ToDoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.todo_item, parent, false);
        final ToDoHolder toDoHolder = new ToDoHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TodoItem item = (TodoItem) view.getTag();

                if(listMode == ListMode.LIST){
                    AddTodoActivity.openTodo(context,item);
                }else{
                    boolean currenteSelection = toDoHolder.chSelection.isChecked();
                    toDoHolder.chSelection.setChecked(!currenteSelection);
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

        //colocar o título no layout
        holder.tvTitle.setText(title);
        if (listMode == ListMode.LIST){
            holder.chSelection.setVisibility(View.GONE);
        }else{
            holder.chSelection.setVisibility(View.VISIBLE);
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