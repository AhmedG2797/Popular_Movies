package ahmedG2797.popularmovies2.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.ArrayList;
import java.util.List;

import ahmedG2797.popularmovies2.Activities.MovieDetails;
import ahmedG2797.popularmovies2.Models.Movie;
import ahmedG2797.popularmovies2.R;

import static ahmedG2797.popularmovies2.Activities.MainActivity.BASE_POSTER_URL;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    Context context;
    List<Movie> movieList;

    public MoviesAdapter(Context context) {
        this.context = context;
        movieList = new ArrayList<>();
    }

    public void addNewData(List<Movie> list) {
        movieList.clear();
        movieList.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.movie_item, parent, false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder holder, int position) {

        Movie movie = movieList.get(position);

        holder.movie_title.setText(movie.getTitle());

        Uri posterUrl = null;
        String posterPath = movie.getPosterPath();
        if (posterPath != null && !posterPath.isEmpty())
            posterUrl = Uri.parse(BASE_POSTER_URL.concat(posterPath));

        Glide.with(context).load(posterUrl).asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(new BitmapImageViewTarget(holder.movie_poster) {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                        super.onResourceReady(bitmap, anim);
                        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(Palette palette) {
                                holder.movie_title_background.setBackgroundColor(palette.getVibrantColor(
                                        ContextCompat.getColor(context, R.color.black_translucent_60)));
                            }
                        });
                    }
                });
    }

    @Override
    public int getItemCount() {
        if (movieList.isEmpty())
            return 0;
        else
            return movieList.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        private ImageView movie_poster;
        private TextView movie_title;
        private View movie_title_background;

        private RelativeLayout container;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            movie_poster = itemView.findViewById(R.id.movie_poster);
            movie_title = itemView.findViewById(R.id.movie_title);
            movie_title_background = itemView.findViewById(R.id.title_background);

            container = itemView.findViewById(R.id.movie_container);

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Movie selectedMovie = movieList.get(getAdapterPosition());

                    Intent intent = new Intent(context, MovieDetails.class);
                    intent.putExtra("selectedMovie", selectedMovie);
                    context.startActivity(intent);
                }
            });

        }
    }
}
