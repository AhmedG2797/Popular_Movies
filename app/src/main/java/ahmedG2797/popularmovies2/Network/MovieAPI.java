package ahmedG2797.popularmovies2.Network;

import ahmedG2797.popularmovies2.Models.MoviesResponse;
import ahmedG2797.popularmovies2.Models.ReviewsResponse;
import ahmedG2797.popularmovies2.Models.TrailersResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MovieAPI {

    @GET("movie/popular?api_key=765b9586ba3cfa76d539fa092becfedd")
    Call<MoviesResponse> getPopularMovies();

    @GET("movie/top_rated?api_key=765b9586ba3cfa76d539fa092becfedd")
    Call<MoviesResponse> getTopRatedMovies();

    @GET("movie/{id}/videos?api_key=765b9586ba3cfa76d539fa092becfedd")
    Call<TrailersResponse> getTrailers(@Path("id") int id);

    @GET("movie/{id}/reviews?api_key=765b9586ba3cfa76d539fa092becfedd")
    Call<ReviewsResponse> getReviews(@Path("id") int id);
}
