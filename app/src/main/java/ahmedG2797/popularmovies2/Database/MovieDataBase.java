package ahmedG2797.popularmovies2.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import ahmedG2797.popularmovies2.Models.Movie;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class MovieDataBase extends RoomDatabase {

    private static MovieDataBase movieDataBase;
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "movie_db";

    public static MovieDataBase getInstance(Context context) {
        if (movieDataBase == null) {
            synchronized (LOCK) {
                movieDataBase = Room.databaseBuilder(context.getApplicationContext(),
                        MovieDataBase.class, DATABASE_NAME).build();
            }
        }

        return movieDataBase;
    }

    public abstract MovieDAO getDAO();
}
