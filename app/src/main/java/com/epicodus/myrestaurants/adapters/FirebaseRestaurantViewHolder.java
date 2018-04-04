package com.epicodus.myrestaurants.adapters;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.myrestaurants.Constants;
import com.epicodus.myrestaurants.R;
import com.epicodus.myrestaurants.models.Restaurant;
import com.epicodus.myrestaurants.ui.RestaurantDetailActivity;
import com.epicodus.myrestaurants.util.ItemTouchHelperViewHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;

public class FirebaseRestaurantViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;

    View mView;
    Context mContext;
    public ImageView mRestaurantImageView;

    public FirebaseRestaurantViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
    }

    public void bindRestaurant(Restaurant restaurant) {
        mRestaurantImageView = (ImageView) mView.findViewById(R.id.restaurantImageView);
        TextView mNameTextView = mView.findViewById(R.id.restaurantNameTextView);
        TextView mCategoryTextView = mView.findViewById(R.id.categoryTextView);
        TextView mRatingTextView = mView.findViewById(R.id.ratingTextView);

        if (!restaurant.getImageUrl().contains("http")) {
            try {
                Bitmap imageBitmap = decodeFromFirebaseBase64(restaurant.getImageUrl());
                mRestaurantImageView.setImageBitmap(imageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Picasso.with(mContext)
                    .load(restaurant.getImageUrl())
                    .resize(MAX_WIDTH, MAX_HEIGHT)
                    .centerCrop()
                    .into(mRestaurantImageView);

            mNameTextView.setText(restaurant.getName());
            mCategoryTextView.setText(restaurant.getCategories().get(0));
            mRatingTextView.setText("Rating: " + restaurant.getRating() + "/5");
        }
        mNameTextView.setText(restaurant.getName());
        mCategoryTextView.setText(restaurant.getCategories().get(0));
        mRatingTextView.setText("Rating: " + restaurant.getRating() + "/5");
    }
    @Override
    public void onItemSelected() {
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(mContext, R.animator.drag_scale_on);
        set.setTarget(itemView);
        set.start();
//        itemView.animate()
//                .alpha(0.7f)
//                .scaleX(0.9f)
//                .scaleY(0.9f)
//                .setDuration(500);
    }

    @Override
    public void onItemClear() {
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(mContext, R.animator.drag_scale_off);
        set.setTarget(itemView);
        set.start();
//        itemView.animate()
//                .alpha(1f)
//                .scaleX(1f)
//                .scaleY(1f);
    }

    public static Bitmap decodeFromFirebaseBase64(String image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }
}

