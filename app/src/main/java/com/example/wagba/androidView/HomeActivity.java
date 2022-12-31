package com.example.wagba.androidView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.wagba.R;
import com.example.wagba.androidView.cartFragment.CartFragment;
import com.example.wagba.androidView.orderHistoryFragment.OrderHistoryFragment;
import com.example.wagba.androidView.restaurantFragment.RestaurantsFragment;
import com.example.wagba.data.models.RestaurantModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity{

    private Fragment restaurantsFragment = new RestaurantsFragment();
    private Fragment orderHistoryFragment = new OrderHistoryFragment();
    private Fragment cartFragment = new CartFragment();
    private ArrayList<RestaurantModel> restaurantModels= new ArrayList<>();
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        replaceFragment(restaurantsFragment);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.order_history_menu:
                        replaceFragment(orderHistoryFragment);
                        break;
                    case R.id.cart_menu:
                        replaceFragment(cartFragment);
                        break;
                    case R.id.restaurants_menu:
                        replaceFragment(restaurantsFragment);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.home_fragment, fragment);
        fragmentTransaction.commit();
    }

}