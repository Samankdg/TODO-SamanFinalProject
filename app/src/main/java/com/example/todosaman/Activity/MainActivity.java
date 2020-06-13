package com.example.todosaman.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.SearchView;

import android.widget.Toast;

import com.example.todosaman.R;
import com.example.todosaman.Database.Tables.TODO;
import com.example.todosaman.Adapter.TODOAdapter;
import com.example.todosaman.ViewModel.TODOViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_TASK_REQUEST = 1;
    public static final int EDIT_TASK_REQUEST = 2;
    private TODOAdapter todoAdapter = new TODOAdapter() ;

    private com.example.todosaman.ViewModel.TODOViewModel TODOViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FloatingActionButton buttonAddNote = findViewById(R.id.button_add_note);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditTaskActivity.class);
                startActivityForResult(intent, ADD_TASK_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

//        final TODOAdapter adapter = new TODOAdapter();
        recyclerView.setAdapter(todoAdapter);

        TODOViewModel = new ViewModelProvider(this).get(TODOViewModel.class);
        TODOViewModel.getAllTasks().observe(this, new Observer<List<TODO>>() {
            @Override
            public void onChanged(@Nullable List<TODO> todos) {
                todoAdapter.setTODO(todos);
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                TODOViewModel.delete(todoAdapter.getTaskAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "TODO Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        todoAdapter.setOnItemClickListener(new TODOAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(TODO TODO) {
                Intent intent = new Intent(MainActivity.this, AddEditTaskActivity.class);
                intent.putExtra(AddEditTaskActivity.EXTRA_ID, TODO.getId());
                intent.putExtra(AddEditTaskActivity.EXTRA_TITLE, TODO.getTitle());
                intent.putExtra(AddEditTaskActivity.EXTRA_DESCRIPTION, TODO.getDescription());
                intent.putExtra(AddEditTaskActivity.EXTRA_DATE_BUTTON, TODO.getDate_button());
                intent.putExtra(AddEditTaskActivity.EXTRA_TIME_BUTTON, TODO.getTime_button());
                intent.putExtra(AddEditTaskActivity.EXTRA_PRIORITY, TODO.getPriority());
                startActivityForResult(intent, EDIT_TASK_REQUEST);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_TASK_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditTaskActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditTaskActivity.EXTRA_DESCRIPTION);
            String date_button = data.getStringExtra(AddEditTaskActivity.EXTRA_DATE_BUTTON);
            String time_button = data.getStringExtra(AddEditTaskActivity.EXTRA_TIME_BUTTON);
            int priority = data.getIntExtra(AddEditTaskActivity.EXTRA_PRIORITY, 1);


            TODO TODO = new TODO(title, description, date_button, time_button, priority);
            TODOViewModel.insert(TODO);

            Toast.makeText(this, "TODO Added", Toast.LENGTH_SHORT).show();
        }
        else if (requestCode == EDIT_TASK_REQUEST && resultCode == RESULT_OK)
        {
            int id = data.getIntExtra(AddEditTaskActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "TODO Not Updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String title = data.getStringExtra(AddEditTaskActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditTaskActivity.EXTRA_DESCRIPTION);
            String date_button = data.getStringExtra(AddEditTaskActivity.EXTRA_DATE_BUTTON);
            String time_button = data.getStringExtra(AddEditTaskActivity.EXTRA_TIME_BUTTON);
            int priority = data.getIntExtra(AddEditTaskActivity.EXTRA_PRIORITY, 1);

            TODO TODO = new TODO(title, description, date_button, time_button, priority);
            TODO.setId(id);
            TODOViewModel.update(TODO);

            Toast.makeText(this, "TODO Updated", Toast.LENGTH_SHORT).show();

        }
        else {
            Toast.makeText(this, "TODO not Added", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                todoAdapter.getFilter().filter(newText);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_tasks:
                TODOViewModel.deleteAllTasks();
                Toast.makeText(this, "All TODOs Deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:

//            case R.id.logout:
//                startActivity(new Intent(MainActivity.this, ActivityLogin.class));

        }
        return super.onOptionsItemSelected(item);
    }
}

