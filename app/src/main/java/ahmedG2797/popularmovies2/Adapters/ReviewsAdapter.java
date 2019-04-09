package ahmedG2797.popularmovies2.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ahmedG2797.popularmovies2.Models.Review;
import ahmedG2797.popularmovies2.R;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder> {

    private Context context = null;
    private List<Review> reviewList = null;

    public ReviewsAdapter(Context context) {
        this.context = context;
        this.reviewList = new ArrayList<>();
    }

    public void addNewReviews (List<Review> reviewList){
        this.reviewList.clear();
        this.reviewList = reviewList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.review, parent, false);

        return new ReviewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsViewHolder holder, int position) {

        Review review = reviewList.get(position);

        holder.review_author.setText(review.getAuthor());
        holder.review_content.setText(review.getContent());
    }

    @Override
    public int getItemCount() {
        if (reviewList.isEmpty()) {
            return 0;
        } else {
            return reviewList.size();
        }
    }


    public class ReviewsViewHolder extends RecyclerView.ViewHolder {

        TextView review_author, review_content;
        public ReviewsViewHolder(@NonNull View itemView) {
            super(itemView);

            review_author = itemView.findViewById(R.id.review_author);
            review_content = itemView.findViewById(R.id.review_content);
        }
    }
}
