package com.epicodus.myrestaurants.util;

import com.epicodus.myrestaurants.models.Restaurant;

import java.util.ArrayList;

/**
 * Created by Guest on 4/4/18.
 */

public interface OnRestaurantSelectedListener  {
    public void onRestaurantSelected(Integer position, ArrayList<Restaurant> restaurants, String source);

}
