package com.ingrid.todolist.activities.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ingrid.todolist.R;
import com.ingrid.todolist.activities.AddTodoActivity;
import com.ingrid.todolist.model.TodoItem;

import java.util.List;

public class TodosAdapter extends RecyclerView.Adapter<TodosAdapter.ToDoHolder> {
    private Context context;
    private List<TodoItem> items;

    public TodosAdapter(Context context, List<TodoItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ToDoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.todo_item, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TodoItem item = (TodoItem) view.getTag();


                AddTodoActivity.openTodo(context,item);
            }
        });

        return new ToDoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoHolder holder, int position) {
        //pegar o título a partir da posição
        TodoItem item = this.items.get(position);
        String title = item.getTitle();

        //colocar o título no layout
        holder.tvTitle.setText(title);

        holder.view.setTag(item);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    class ToDoHolder extends RecyclerView.ViewHolder {
        private final View view;
        private final TextView tvTitle;

        public ToDoHolder(View view) {
            super(view);
            this.view = view;
            this.tvTitle = view.findViewById(R.id.tvTitle);

            view.setLongClickable(true);
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Toast.makeText(view.getContext(),"Long Press", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }
    }
}