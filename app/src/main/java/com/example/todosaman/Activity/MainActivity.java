package com.example.todosaman.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
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
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final int ADD_TASK_REQUEST = 1;
    public static final int EDIT_TASK_REQUEST = 2;
    private TODOAdapter todoAdapter = new TODOAdapter();

    private com.example.todosaman.ViewModel.TODOViewModel TODOViewModel;

    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //navigation menu
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);


        //add task
        FloatingActionButton buttonAddTask = findViewById(R.id.button_add_note);
        buttonAddTask.setOnClickListener(new View.OnClickListener() {
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

        //TODO View Model
        TODOViewModel = new ViewModelProvider(this).get(TODOViewModel.class);
        TODOViewModel.getAllTasks().observe(this, new Observer<List<TODO>>() {
            @Override
            public void onChanged(@Nullable List<TODO> todos) {
                todoAdapter.setTODO(todos);
            }
        });

        //Swipe Features to delete Task
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

    //remove navigation bar on back pressed
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        //for Search Bar
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
       return true;
    }

    //listener for navigation menu click
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_calendar:
                Intent intent1 = new Intent(MainActivity.this, CalendarActivity.class);
                startActivity(intent1);
                break;

            case R.id.nav_delete:
                TODOViewModel.deleteAllTasks();
                Toast.makeText(this, "All TODOs Deleted", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.nav_logout:
                Intent intent2 = new Intent(MainActivity.this, ActivityLogin.class);
                startActivity(intent2);
                finish();
                break;

            case R.id.nav_exit:
                finish();
            default:
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}

