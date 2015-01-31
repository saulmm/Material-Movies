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
import com.hackvg.android.view.OnItemClickListener;

import java.util.List;

/**
 * Created by saulmm on 31/01/15.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MovieViewHolder> {

//    private final List<TvMovie> movieList;
    public OnItemClickListener onItemClickListener;
    private Context context;

    public MoviesAdapter(List<TvMovie> movieList) {

//        this.movieList = movieList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View rowView = LayoutInflater.from(viewGroup.getContext())
            .inflate(R.layout.item_movie, viewGroup, false);

        this.context = viewGroup.getContext();

        return new MovieViewHolder(rowView, onItemClickListener);

    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

        holder.titleTextView.setText("Movie "+position);
        holder.authorTextView.setText("AwesomeAuthor");
    }

    @Override
    public int getItemCount() {

        return 20;
    }
}

class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private final OnItemClickListener onClickListener;
     TextView titleTextView;
     TextView authorTextView;
     ImageView coverImageView;

    public MovieViewHolder(View itemView, OnItemClickListener onClickListener) {

        super(itemView);

        titleTextView = (TextView) itemView.findViewById(R.id.item_movie_title);
        authorTextView = (TextView) itemView.findViewById(R.id.item_movie_author);
        coverImageView = (ImageView) itemView.findViewById(R.id.item_movie_cover);
        coverImageView.setOnClickListener(this);
        this.onClickListener = onClickListener;

    }

    @Override
    public void onClick(View v) {

        onClickListener.onClick(v, getPosition());
    }
}



