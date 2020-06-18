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





//        private static AppDatabase instance;
//
//        public abstract TODODao taskDao();
//
//        public static synchronized AppDatabase getInstance(Context context){
//            if (instance == null) {
//                instance = Room.databaseBuilder(context.getApplicationContext(),
//                        AppDatabase.class, "task_database")
//                        .fallbackToDestructiveMigration()
//                        .addCallback(roomCallback)
//                        .build();
//            }
//            return instance;
//        }
//        private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
//            @Override
//            public void onCreate(@NonNull SupportSQLiteDatabase db) {
//                super.onCreate(db);
//                new PopulateDbAsyncTask(instance).execute();
//            }
//        };
//        private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
//            private TODODao TODODao;
//            private PopulateDbAsyncTask(AppDatabase db) {
//                TODODao = db.taskDao();
//            }
//            @Override
//            protected Void doInBackground(Void... voids) {
//                TODODao.insert(new TODO("Title 1", "Description 1", "Add Date ?", "Your Time ?",1));
//                return null;
//            }
//        }
//    }
