package com.example.wagba;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class MealsActivity extends AppCompatActivity {

    private CardView infoCard;
    private SearchView searchView;
    private TextView rest_name_tv, rest_phone_tv, rest_description_tv, rest_address_tv;
    private ImageView rest_image_iv;
    private HashMap<String, MealModel> mealModels;
    private MealsAdapter mealsAdapter;
    private RecyclerView recyclerView;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals);

        rest_name_tv = (TextView) findViewById(R.id.cart_name);
        rest_address_tv = (TextView) findViewById(R.id.rest_address);
        rest_phone_tv = (TextView) findViewById(R.id.rest_phone);
        rest_description_tv = (TextView) findViewById(R.id.cart_price);
        rest_image_iv = (ImageView) findViewById(R.id.rest_image);

        intent = getIntent();
        String restaurantName = intent.getStringExtra("restaurantName");
        String restaurantDescription = intent.getStringExtra("restaurantDescription");
        String restaurantAddress = intent.getStringExtra("restaurantAddress");
        String restaurantPhone = intent.getStringExtra("restaurantPhone");
        String restaurantImage = intent.getStringExtra("restaurantImage");
        mealModels = (HashMap<String, MealModel>) intent.getSerializableExtra("meals");
        Collection<MealModel> values = mealModels.values();

        ArrayList<MealModel> mealsList = new ArrayList<>(values);

        Log.i("meals", "mealsList: "+mealsList);

        rest_name_tv.setText(restaurantName);
        rest_address_tv.setText(restaurantAddress);
        rest_phone_tv.setText(restaurantPhone);
        rest_description_tv.setText(restaurantDescription);

        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttp3Downloader(this));
        builder.build().load(restaurantImage).into(rest_image_iv);

        recyclerView = (RecyclerView) findViewById(R.id.mealList);
        recyclerView.setHasFixedSize(true);

        mealsAdapter = new MealsAdapter(this, mealsList);
        recyclerView.setAdapter(mealsAdapter);

    }
}