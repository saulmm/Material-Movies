package com.hackvg.android.views.adapters;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hackvg.android.R;
import com.hackvg.android.utils.RecyclerViewClickListener;
import com.hackvg.model.entities.Movie;
import com.hackvg.common.utils.Constants;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MoviesAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private Context mContext;
    private List<Movie> mMovieList;
    private RecyclerViewClickListener mRecyclerClickListener;

    public MoviesAdapter(List<Movie> mMovieList) {

        this.mMovieList = mMovieList;
    }

    public List<Movie> getMovieList() {

        return mMovieList;
    }

    public void setRecyclerListListener(RecyclerViewClickListener mRecyclerClickListener) {
        this.mRecyclerClickListener = mRecyclerClickListener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View rowView = LayoutInflater.from(viewGroup.getContext())
            .inflate(R.layout.item_movie, viewGroup, false);

        this.mContext = viewGroup.getContext();

        return new MovieViewHolder(rowView, mRecyclerClickListener);
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, final int position) {

        Movie selectedMovie = mMovieList.get(position);

        holder.titleTextView.setText(selectedMovie.getTitle());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            holder.coverImageView.setTransitionName("cover" + position);

        String posterURL = Constants.BASIC_STATIC_URL + selectedMovie.getPoster_path();

        Picasso.with(mContext)
            .load(posterURL)
            .fit()
            .centerCrop()
            .into(holder.coverImageView, new Callback() {
                @Override
                public void onSuccess() {

                    mMovieList.get(position).setMovieReady(true);
                }

                @Override
                public void onError() {

                }
            });
    }

    public boolean isMovieReady(int position) {

        return mMovieList.get(position).isMovieReady();
    }

    @Override
    public int getItemCount() {

        return mMovieList.size();
    }

    public void appendMovies(List<Movie> movieList) {

        mMovieList.addAll(movieList);
        notifyDataSetChanged();
    }
}

class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener{

    private final RecyclerViewClickListener onClickListener;
    TextView titleTextView;
    TextView authorTextView;
    ImageView coverImageView;

    public MovieViewHolder(View itemView, RecyclerViewClickListener onClickListener) {

        super(itemView);

        titleTextView = (TextView) itemView.findViewById(R.id.item_movie_title);
        coverImageView = (ImageView) itemView.findViewById(R.id.item_movie_cover);
        coverImageView.setDrawingCacheEnabled(true);
        coverImageView.setOnTouchListener(this);
        this.onClickListener = onClickListener;
    }

    public void setReady(boolean ready) {

        coverImageView.setTag(ready);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_UP && event.getAction() != MotionEvent.ACTION_MOVE) {

            onClickListener.onClick(v, getPosition(), event.getX(), event.getY());
        }
        return true;
    }
}



