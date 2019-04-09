package ahmedG2797.popularmovies2.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ahmedG2797.popularmovies2.Models.Trailer;
import ahmedG2797.popularmovies2.R;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailerViewHolder> {

    private Context context = null;
    private List<Trailer> trailerList = null;

    public TrailersAdapter(Context context) {
        this.context = context;
        this.trailerList = new ArrayList<>();
    }

    public void addNewTrailers(List<Trailer> trailerList) {
        this.trailerList.clear();
        this.trailerList.addAll(trailerList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.trailer, parent, false);

        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {

        Trailer trailer = trailerList.get(position);

        Uri thumbnail = null;
        String thumbnailUrl = Trailer.getThumbnailUrl(trailer);
        if (thumbnailUrl != null && !thumbnailUrl.isEmpty()) {
            thumbnail = Uri.parse(thumbnailUrl);
        }

        Glide.with(context)
                .load(thumbnail)
                .placeholder(R.color.colorPrimary)
                .error(R.color.colorPrimary)
                .into(holder.video_thumbnail);
    }

    @Override
    public int getItemCount() {
        if (trailerList.isEmpty()) {
            return 0;
        } else {
            return trailerList.size();
        }
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder{

        private ImageView video_thumbnail;

        public TrailerViewHolder(@NonNull View itemView) {
            super(itemView);

            video_thumbnail = itemView.findViewById(R.id.video_thumbnail);
            video_thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Trailer trailer = trailerList.get(getAdapterPosition());
                    String videoUrl = Trailer.getUrl(trailer);
                    if (videoUrl != null && !videoUrl.isEmpty()) {
                        Intent playVideoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl));
                        context.startActivity(Intent.createChooser(playVideoIntent, "Open with"));
                    } else {

                    }

                }
            });
        }
    }
}
