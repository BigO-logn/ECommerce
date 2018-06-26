package com.behadd.e_commerce;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    Context mContext;
    private ArrayList<String> mMovie;
    private ArrayList<String> mImage;

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView mTvMovie;
        public ImageView mIvImage;
        FrameLayout parentLayout;

        Button delete;
        RatingBar ratingBar;

        public ViewHolder(View v){
            super(v);
            mTvMovie=v.findViewById(R.id.tvSampleTV);
            mIvImage= v.findViewById(R.id.sampleImage);
            parentLayout=v.findViewById(R.id.parentLayout);
            delete=v.findViewById(R.id.btnRemove);
            ratingBar=v.findViewById(R.id.ratingBar);
        }

        public TextView getmTvMovie(){
            return mTvMovie;
        }
    }


    //Constructor
    public MyAdapter(Context context, ArrayList<String> myMovieName, ArrayList<String> myImage) {
        mMovie = myMovieName;
        mImage= myImage;
        mContext= context;
    }

    //Naye views banaane, ke dikhaana kya hai vo bataana
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View v=  LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sample_text_view,viewGroup,false);
        return new ViewHolder(v);
    }


    @Override
    public int getItemCount() {
        return mMovie.size();
    }

    //Replace contents of view jo layout manager ne invoke kiye the
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position){


        viewHolder.getmTvMovie().setText(mMovie.get(position));
        Glide.with(mContext).asBitmap().load(mImage.get(position)).into(viewHolder.mIvImage);

        //On Rating Click
        viewHolder.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                float ratingNum= ratingBar.getRating();
                int index= mMovie.indexOf(mMovie.get(position));
                Intent goToDetails = new Intent(mContext,MovieDetailsActivity.class);
                goToDetails.putExtra("Movie_Name",mMovie.get(position));
                goToDetails.putExtra("Movie_Rating",ratingNum);
                goToDetails.putExtra("Movie_Poster",mImage.get(position));
                goToDetails.putExtra("Movie_Position", index);
                mContext.startActivity(goToDetails);
            }
        });

        //On Delete Click
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Deleted "+mMovie.get(position), Toast.LENGTH_SHORT).show();
                mMovie.remove(position);
                mImage.remove(position);
                notifyItemRangeChanged(position, mMovie.size());
                notifyItemRemoved(position);
            }
        });

        //On Card Click
        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent goToDetails = new Intent(mContext,MovieDetailsActivity.class);
                int index= mMovie.indexOf(mMovie.get(position));

                goToDetails.putExtra("Movie_Name",mMovie.get(position));
                goToDetails.putExtra("Movie_Poster",mImage.get(position));
                goToDetails.putExtra("Movie_Position", index);

                mContext.startActivity(goToDetails);
            }
        });
    }

}