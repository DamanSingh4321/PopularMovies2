package com.singh.daman.popularmovies2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.like.IconType;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.singh.daman.popularmovies2.Activity.DetailActivity;
import com.singh.daman.popularmovies2.Database.DatabaseHandler;
import com.singh.daman.popularmovies2.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.singh.daman.popularmovies2.R.layout.movie;

/**
 * Created by daman on 11/9/16.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<String> id, moviesposter, overview, date, title, vote;

     class MyViewHolder extends RecyclerView.ViewHolder {

         ImageView imageView;
         LikeButton btnfav;
         CardView mCardView;

         MyViewHolder(View v) {
            super(v);
            mCardView = (CardView) v.findViewById(R.id.card_view);
            imageView = (ImageView) v.findViewById(R.id.grid_image);
            btnfav = (LikeButton) v.findViewById(R.id.fav_button);
            btnfav.setIcon(IconType.Star);
        }
    }

    public MoviesAdapter(Context c, ArrayList<String> id,
                         ArrayList<String> moviesposter, ArrayList<String> overview,
                         ArrayList<String> date, ArrayList<String> title, ArrayList<String> vote) {
        mContext = c;
        this.id = id;
        this.moviesposter = moviesposter;
        this.overview = overview;
        this.date = date;
        this.title = title;
        this.vote = vote;
    }

    @Override
    public MoviesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        MyViewHolder holder;
        v = LayoutInflater.from(parent.getContext()).inflate(movie, parent, false);
        holder = new MyViewHolder(v);
        holder.mCardView.setTag(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        try {
            final DatabaseHandler handler = new DatabaseHandler(mContext);
            Picasso.with(mContext)
                    .load(moviesposter.get(position))
                    .placeholder(R.drawable.loading).fit()
                    .into(holder.imageView);
            final String idpos = id.get(position);

            if (handler.CheckIsFAv(idpos)) {
                holder.btnfav.setLiked(true);
            } else
                holder.btnfav.setLiked(false);
            holder.btnfav.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    handler.addFavs(idpos ,
                            title.get(holder.getAdapterPosition()), moviesposter.get(holder.getAdapterPosition()),
                            vote.get(holder.getAdapterPosition()), date.get(holder.getAdapterPosition()),
                            overview.get(holder.getAdapterPosition()));
                    holder.btnfav.setLiked(true);
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    handler.deleteFav(idpos);
                    holder.btnfav.setLiked(false);
                }
            });
            holder.mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, DetailActivity.class);
                    Bundle extras = new Bundle();
                    extras.putString("EXTRA_IMAGE", moviesposter.get(holder.getAdapterPosition()));
                    extras.putString("EXTRA_OVERVIEW", overview.get(holder.getAdapterPosition()));
                    extras.putString("EXTRA_DATE", date.get(holder.getAdapterPosition()));
                    extras.putString("EXTRA_TITLE", title.get(holder.getAdapterPosition()));
                    extras.putString("EXTRA_VOTE", vote.get(holder.getAdapterPosition()));
                    extras.putString("EXTRA_ID", id.get(holder.getAdapterPosition()));
                    intent.putExtras(extras);
                    mContext.startActivity(intent);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return id.size();
    }
}
