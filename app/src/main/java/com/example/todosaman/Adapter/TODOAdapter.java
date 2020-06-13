package com.example.todosaman.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todosaman.Database.Tables.TODO;
import com.example.todosaman.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
public class TODOAdapter extends RecyclerView.Adapter<TODOAdapter.TODOHolder> implements Filterable {

    private List<TODO> todo = new ArrayList<>();
    List<TODO> todoList;

    private OnItemClickListener listener;

    @NonNull
    @Override
    public TODOHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_item, parent, false);
        return new TODOHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull TODOAdapter.TODOHolder holder, int position) {
        TODO currentTask = todo.get(position);
        holder.textViewTitle.setText(currentTask.getTitle());
        holder.textViewDescription.setText(currentTask.getDescription());
        holder.textViewDateButton.setText(currentTask.getDate_button());
        holder.textViewTimeButton.setText(currentTask.getTime_button());
        holder.textViewPriority.setText(String.valueOf(currentTask.getPriority()));

    }
    @Override
    public int getItemCount() {
        return todo.size();
    }
    public void setTODO(List<TODO> todo) {
        this.todo = todo;
        this.todoList = new ArrayList<>(todo);
        notifyDataSetChanged();
    }
    public TODO getTaskAt(int position) {
        return todo.get(position);
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<TODO> filteredList = new ArrayList<>();
            if (charSequence.toString().isEmpty()){
                filteredList.addAll(todoList);
            }else {
                for (TODO todo: todoList) {
                    if (todo.getTitle().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filteredList.add(todo);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults filterResults) {
                todo.clear();
                todo.addAll((Collection<? extends TODO>) filterResults.values);
                notifyDataSetChanged();
        }
    };

    class TODOHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPriority;
        private TextView textViewDateButton;
        private TextView textViewTimeButton;

        public TODOHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewDateButton = itemView.findViewById(R.id.text_view_date_button);
            textViewTimeButton = itemView.findViewById(R.id.text_view_time_button);
            textViewPriority = itemView.findViewById(R.id.text_view_priority);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(todo.get(position));
                    }
                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(TODO todo);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
