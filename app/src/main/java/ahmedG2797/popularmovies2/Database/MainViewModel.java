package ahmedG2797.popularmovies2.Database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import ahmedG2797.popularmovies2.Models.Movie;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<Movie>> movies;

    public MainViewModel(Application application) {
        super(application);

        MovieDataBase movieDataBase = MovieDataBase.getInstance(this.getApplication());
        movies = movieDataBase.getDAO().getItems();
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }
}
