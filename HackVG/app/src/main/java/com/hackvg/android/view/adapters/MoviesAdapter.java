package com.hackvg.android.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hackvg.android.R;
import com.hackvg.android.model.entities.TvMovie;
import com.hackvg.android.utils.Constants;
import com.hackvg.android.view.HackVGClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MoviesAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private final List<TvMovie> movieList;
    public HackVGClickListener hackVGClickListener;
    private Context context;

    public MoviesAdapter(List<TvMovie> movieList) {

        this.movieList = movieList;
    }

    public void setHackVGClickListener(HackVGClickListener hackVGClickListener) {
        this.hackVGClickListener = hackVGClickListener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View rowView = LayoutInflater.from(viewGroup.getContext())
            .inflate(R.layout.item_movie, viewGroup, false);

        this.context = viewGroup.getContext();

        return new MovieViewHolder(rowView, hackVGClickListener);

    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

        TvMovie selectedMovie = movieList.get(position);

        holder.titleTextView.setText(selectedMovie.getTitle());
        holder.authorTextView.setText(selectedMovie.getRelease_date());

        String posterURL = Constants.POSTER_PREFIX + selectedMovie.getPoster_path();

        Picasso.with(context)
            .load(posterURL)
            .into(holder.coverImageView);
    }

    @Override
    public int getItemCount() {

        return movieList.size();
    }
}

class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private final HackVGClickListener onClickListener;
     TextView titleTextView;
     TextView authorTextView;
     ImageView coverImageView;

    public MovieViewHolder(View itemView, HackVGClickListener onClickListener) {

        super(itemView);

        titleTextView = (TextView) itemView.findViewById(R.id.item_movie_title);
        authorTextView = (TextView) itemView.findViewById(R.id.item_movie_author);
        coverImageView = (ImageView) itemView.findViewById(R.id.item_movie_cover);
//        coverImageView.setOnClickListener(this);
        this.onClickListener = onClickListener;

    }

    @Override
    public void onClick(View v) {

        onClickListener.onClick(v, getPosition());
    }
}



