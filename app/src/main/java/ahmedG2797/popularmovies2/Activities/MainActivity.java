package ahmedG2797.popularmovies2.Activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ahmedG2797.popularmovies2.Adapters.MoviesAdapter;
import ahmedG2797.popularmovies2.Database.MainViewModel;
import ahmedG2797.popularmovies2.Models.Movie;
import ahmedG2797.popularmovies2.Models.MoviesResponse;
import ahmedG2797.popularmovies2.Network.ApiClient;
import ahmedG2797.popularmovies2.Network.MovieAPI;
import ahmedG2797.popularmovies2.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Snackbar snackbar = null;

    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String BASE_POSTER_URL = "https://image.tmdb.org/t/p/w500";
    public static final String BASE_BACKDROP_URL = "https://image.tmdb.org/t/p/w780";

    public static final String YOUTUBE_VIDEO_URL = "https://www.youtube.com/watch?v=%s";
    public static final String YOUTUBE_THUMBNAIL_URL = "https://img.youtube.com/vi/%s/0.jpg";

    private static final String MOST_POPULAR =
            "http://api.themoviedb.org/3/movie/popular?api_key=765b9586ba3cfa76d539fa092becfedd";
    private static final String TOP_RATED =
            "http://api.themoviedb.org/3/movie/top_rated?api_key=765b9586ba3cfa76d539fa092becfedd";

    private RecyclerView movieRecyclerView = null;
    private ProgressBar progressBar = null;
    private MoviesAdapter movieAdapter = null;

    private List<Movie> movieList = null;
    private List<Movie> favorites = null;

    private MovieAPI movieAPI;

    private String selectedOption = null;
    private SharedPreferences sharedPreferences = null;

    private static int callType = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        snackbar = Snackbar.make(findViewById(R.id.main_activity),
                "Check Your Internet Connection!", Snackbar.LENGTH_INDEFINITE);

        progressBar = findViewById(R.id.main_progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        movieList = new ArrayList<>();
        favorites = new ArrayList<>();

        movieAdapter = new MoviesAdapter(this);

        movieRecyclerView = findViewById(R.id.movies_recycler_view);
        movieRecyclerView.setLayoutManager(new
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        movieRecyclerView.setAdapter(movieAdapter);

        movieAPI = ApiClient.getClient().create(MovieAPI.class);

        String PREFERENCES_NAME = "movies_Preferences";
        sharedPreferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);

        if (sharedPreferences.getString("selected_option", null) == null) {

            Toast.makeText(getApplicationContext(), "Run Firts Time", Toast.LENGTH_LONG).show();

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("selected_option", getString(R.string.most_popular));
            editor.apply();
        }

        setupViewModel();
        Log.d("Testing:::::::::::::::>", "onCreateEnd");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Testing:::::::::::::::>", "onResume");

        selectedOption = sharedPreferences.getString("selected_option", getString(R.string.most_popular));
        Log.d("Testing:::::::::::::::>", "onResume, " + selectedOption);
        callType = 1;
        displayData(selectedOption);
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("selected_option", selectedOption);
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case (R.id.menu_most_popular):
                selectedOption = getString(R.string.most_popular);
                break;
            case (R.id.menu_top_rated):
                selectedOption = getString(R.string.top_rated);
                break;
            case (R.id.menu_favorites):
                selectedOption = getString(R.string.favorites);
                break;
        }

        Log.d("Testing:::::::::::::::>", "onOptionsItemSelected, " + selectedOption);
        callType = 2;
        displayData(selectedOption);

        return super.onOptionsItemSelected(item);
    }

    private void displayData(String option) {
        Call<MoviesResponse> responseCall;

        if (option.equals(getString(R.string.most_popular))) {
            Log.d("Testing:::::::::::::::>", "displayData, most_popular");

            responseCall = movieAPI.getPopularMovies();

            if (isConnected()) {
                if (snackbar != null && snackbar.isShown()) {
                    snackbar.dismiss();
                }

                responseCall.enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                        Log.d("Testing:::::::::::::::>", "getDataFromAPI, onResponse");
                        movieList.clear();
                        movieList = response.body().getResults();

                        progressBar.setVisibility(View.GONE);
                        movieAdapter.addNewData(movieList);
                    }

                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {

                    }
                });
            } else {
                progressBar.setVisibility(View.GONE);
                snackbar.show();
            }
        } else if (option.equals(getString(R.string.top_rated))) {
            Log.d("Testing:::::::::::::::>", "displayData, top_rated");

            responseCall = movieAPI.getTopRatedMovies();

            if (isConnected()) {
                if (snackbar != null && snackbar.isShown()) {
                    snackbar.dismiss();
                }

                responseCall.enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                        Log.d("Testing:::::::::::::::>", "getDataFromAPI, onResponse");
                        movieList.clear();
                        movieList = response.body().getResults();

                        progressBar.setVisibility(View.GONE);
                        movieAdapter.addNewData(movieList);
                    }

                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {

                    }
                });
            } else {
                progressBar.setVisibility(View.GONE);
                snackbar.show();
            }
        } else if (option.equals(getString(R.string.favorites))) {
            if (callType == 2) {
                displayFavorites(2);
            }
        }
    }

    private void displayFavorites(int callType) {
        if (callType == 2 || callType == 3) {
            if (!favorites.isEmpty()) {
                movieList.clear();
                movieList.addAll(favorites);

                Log.d("Testing:::::::::::::::>", "displayData, favorites");
                progressBar.setVisibility(View.GONE);
                movieAdapter.addNewData(movieList);
            } else {
                movieList.clear();
                progressBar.setVisibility(View.GONE);
                movieAdapter.addNewData(movieList);
                Log.d("Testing:::::::::::::::>", "displayData, Favorites List Is Empty.");
                Toast.makeText(MainActivity.this, "Favorite List Is Empty.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setupViewModel() {
        final MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> list) {
                if (list != null && !list.isEmpty()) {
                    Log.d("Testing:::::::::::::::>", "setupViewModel, onChanged, list != null");
                    favorites.clear();
                    Log.d("Testing:::::::::::::::>", "setupViewModel, onChanged, favoritesCleared");
                    favorites.addAll(list);
                    Log.d("Testing:::::::::::::::>", "setupViewModel, onChanged, favorites.addAll");
                } else {
                    favorites.clear();
                    Log.d("Testing:::::::::::::::>", "setupViewModel, onChanged, list == null or empty");
                }

                if (selectedOption.equals(getString(R.string.favorites))) {
                    displayFavorites(3);
                }
            }
        });
        Log.d("Testing:::::::::::::::>", "setupViewModelEnd");
    }

    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
