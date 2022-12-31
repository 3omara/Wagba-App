package com.example.wagba.androidView.mealsActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wagba.R;
import com.example.wagba.data.room.entities.Cart;
import com.example.wagba.data.models.MealModel;
import com.example.wagba.data.room.viewmodel.UserViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

    private UserViewModel userViewModel;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private ArrayList<Cart> carts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals);

        rest_name_tv = (TextView) findViewById(R.id.rest_name);
        rest_address_tv = (TextView) findViewById(R.id.rest_address);
        rest_phone_tv = (TextView) findViewById(R.id.rest_phone);
        rest_description_tv = (TextView) findViewById(R.id.rest_description);
        rest_image_iv = (ImageView) findViewById(R.id.rest_image);

        /////////////////////////
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        String userID = user.getUid();

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        carts = new ArrayList<Cart>();
        ////////////////////////

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

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        mealsAdapter = new MealsAdapter(this, mealsList, restaurantName, userID);
        recyclerView.setAdapter(mealsAdapter);

//        AtomicReference<Boolean> exists = new AtomicReference<>(Boolean.FALSE);
//        AtomicReference<Long> cartID = new AtomicReference<>();

//        userViewModel.getUserCarts(userID).observe(this, userCarts->{
//
//            for (Integer i = 0; i < userCarts.carts.size(); i++) {
//                if (userCarts.carts.get(i).getCartName().equals(restaurantName + "'s Cart")) {
//                    cartID.set(userCarts.carts.get(i).getCartID());
//                    exists.set(Boolean.TRUE);
//                }
//            }
//            if(exists.get()){
//                userViewModel.getCartItems(cartID.get()).observe(this, cartWithCartItems -> {
//                    mealsAdapter.setCartWithCartItems(cartWithCartItems);
//                    mealsAdapter.setCartExists(exists.get());
//                });
//            }
//        });



    }
}