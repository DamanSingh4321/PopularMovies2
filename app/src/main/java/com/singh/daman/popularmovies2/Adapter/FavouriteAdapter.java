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

/**
 * Created by daman on 29/9/16.
 */

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<String> id, moviesposter, overview, date, title, vote, favourite;

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

    public FavouriteAdapter(Context c, ArrayList<String> id,
                         ArrayList<String> moviesposter, ArrayList<String> overview,
                         ArrayList<String> date, ArrayList<String> title, ArrayList<String> vote,
                         ArrayList<String> favourite) {
        mContext = c;
        this.id = id;
        this.moviesposter = moviesposter;
        this.overview = overview;
        this.date = date;
        this.title = title;
        this.vote = vote;
        this.favourite = favourite;
    }

    @Override
    public FavouriteAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        MyViewHolder holder;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie, parent, false);
        holder = new MyViewHolder(v);
        holder.mCardView.setTag(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        try {

            Picasso.with(mContext)
                    .load(moviesposter.get(position))
                    .placeholder(R.drawable.loading).fit()
                    .into(holder.imageView);

            if (favourite.get(position).equals("YES")) {
                holder.btnfav.setLiked(true);
            } else
                holder.btnfav.setLiked(false);
            holder.btnfav.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    DatabaseHandler handler = new DatabaseHandler(mContext);
                    handler.favUpdate("YES", id.get(holder.getAdapterPosition()));
                    favourite.set(holder.getAdapterPosition(), "YES");
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    DatabaseHandler handler = new DatabaseHandler(mContext);
                    handler.favUpdate("NO", id.get(holder.getAdapterPosition()));
                    favourite.set(holder.getAdapterPosition(), "NO");
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
