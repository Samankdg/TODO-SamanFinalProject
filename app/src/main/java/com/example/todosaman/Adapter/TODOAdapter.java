package com.example.todosaman.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todosaman.R;
import com.example.todosaman.Tables.TODO;

public class TODOAdapter extends ListAdapter<TODO, TODOAdapter.NoteHolder> {

    private onItemClickListener listener;


    public TODOAdapter() {
        super(DIFF_CALLBACK);
    }


    private static final DiffUtil.ItemCallback<TODO> DIFF_CALLBACK = new DiffUtil.ItemCallback<TODO>() {
        @Override
        public boolean areItemsTheSame(@NonNull TODO oldItem, @NonNull TODO newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull TODO oldItem, @NonNull TODO newItem) {
            return oldItem.getTitle().equals(newItem.getTitle())&&
                    oldItem.getDescription().equals(newItem.getDescription())
                    &&oldItem.getDate_button().equals(newItem.getDate_button())
                    &&oldItem.getTime_button().equals(newItem.getTime_button())
                    &&oldItem.getPriority() == newItem.getPriority();
        }
    };

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_item, parent, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        TODO currentTODO = getItem(position);
        holder.textViewTitle.setText(currentTODO.getTitle());
        holder.textViewDescription.setText(currentTODO.getDescription());
        holder.textViewDate_Button.setText(currentTODO.getDate_button());
        holder.textViewTime_Button.setText(currentTODO.getTime_button());
        holder.textViewPriority.setText(String.valueOf(currentTODO.getPriority()));
    }



    public TODO getNoteAt(int position) {
        return getItem(position);
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewDate_Button;
        private TextView textViewTime_Button;
        private TextView textViewPriority;
        public CheckBox checkBox;


        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewDate_Button = itemView.findViewById(R.id.text_view_date_button);
            textViewTime_Button = itemView.findViewById(R.id.text_view_time_button);
            textViewPriority = itemView.findViewById(R.id.text_view_priority);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface onItemClickListener {
        void onItemClick(TODO TODO);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

}
