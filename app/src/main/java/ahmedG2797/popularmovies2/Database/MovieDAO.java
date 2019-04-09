package ahmedG2797.popularmovies2.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import ahmedG2797.popularmovies2.Models.Movie;

@Dao
public interface MovieDAO {
    @Insert
    void insert(Movie item);

    @Delete
    void delete(Movie item);

    @Query("SELECT * FROM favorites")
    LiveData<List<Movie>> getItems();

    @Query("SELECT * FROM favorites WHERE id = :id")
    Movie getMovie(int id);
}
