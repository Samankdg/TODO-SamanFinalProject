    package com.example.todosaman.Database;

    import android.content.Context;
    import android.os.AsyncTask;
    import android.util.Log;

    import androidx.annotation.NonNull;
    import androidx.room.Database;
    import androidx.room.Room;
    import androidx.room.RoomDatabase;
    import androidx.sqlite.db.SupportSQLiteDatabase;

    import com.example.todosaman.Database.Tables.TODO;
    import com.example.todosaman.Database.Dao.TODODao;
    import com.example.todosaman.Database.Tables.User;
    import com.example.todosaman.Database.Dao.UserDao;

    import java.util.concurrent.ExecutorService;
    import java.util.concurrent.Executors;

    @Database(entities = {TODO.class, User.class} , version = 1, exportSchema = false)

    public abstract class AppDatabase extends RoomDatabase {

        public abstract UserDao getUserDao();
        private static final String LOG_TAG = AppDatabase.class.getSimpleName();
        private static final Object LOCK = new Object();
        private static String DATABASE_NAME = "todolist";
        static final ExecutorService databaseWriteExecutor =
                Executors.newFixedThreadPool(1);

        private static AppDatabase sInstance;

        public static AppDatabase getInstance(Context context){
            if(sInstance == null){
                synchronized (LOCK){
                    Log.d(LOG_TAG, "Creating a new database instance");
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, AppDatabase.DATABASE_NAME)
                            //.allowMainThreadQueries()
                            .build();
                }
            }
            Log.d(LOG_TAG, "Getting the database instance");
            return sInstance;
        }

        public abstract TODODao taskDao();


    }

