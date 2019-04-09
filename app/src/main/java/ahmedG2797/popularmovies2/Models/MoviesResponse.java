package ahmedG2797.popularmovies2.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MoviesResponse {

    @SerializedName("results")
    @Expose
    private List<Movie> results = null;

    public MoviesResponse() {
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }
}
