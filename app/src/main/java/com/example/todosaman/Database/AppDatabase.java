    package com.example.todosaman.Database;

    import android.content.Context;
    import android.os.AsyncTask;

    import androidx.annotation.NonNull;
    import androidx.room.Database;
    import androidx.room.Room;
    import androidx.room.RoomDatabase;
    import androidx.sqlite.db.SupportSQLiteDatabase;

    import com.example.todosaman.Tables.TODO;
    import com.example.todosaman.Database.Dao.TODODao;
    import com.example.todosaman.Tables.User;
    import com.example.todosaman.Database.Dao.UserDao;

    @Database(entities = {TODO.class, User.class} , version = 1, exportSchema = false)

    public abstract class AppDatabase extends RoomDatabase {

        public abstract UserDao getUserDao();

        private static AppDatabase instance;

        public abstract TODODao taskDao();

        public static synchronized AppDatabase getInstance(Context context){
            if (instance == null) {
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, "task_database")
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build();
            }
            return instance;
        }
        private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                new PopulateDbAsyncTask(instance).execute();
            }
        };
        private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
            private TODODao TODODao;
            private PopulateDbAsyncTask(AppDatabase db) {
                TODODao = db.taskDao();
            }
            @Override
            protected Void doInBackground(Void... voids) {
                TODODao.insert(new TODO("Title 1", "Description 1", "Add Date ?", "Your Time ?",1));
                return null;
            }
        }
    }
