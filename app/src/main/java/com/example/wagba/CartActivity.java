package com.example.wagba;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private ArrayList<CartItem> cartItems;
    private UserViewModel userViewModel;
    private Intent intent;
    private Cart cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        cart = (Cart) getIntent().getSerializableExtra("cart");

        setContentView(R.layout.activity_cart);

        recyclerView = (RecyclerView) findViewById(R.id.items_RecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cartItems = new ArrayList<>();
        itemAdapter = new ItemAdapter(this, cart);
        recyclerView.setAdapter(itemAdapter);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getCartItems(cart.getCartID()).observe(this, cartWithCartItems->{
            if(cartWithCartItems.cartItems != null){
                cartItems.clear();
                for (Integer i = 0; i < cartWithCartItems.cartItems.size(); i++) {
                    Log.i("ROOM", "Item Name: " + cartWithCartItems.cartItems.get(i).getItemName());
                    cartItems.add(cartWithCartItems.cartItems.get(i));
                }
                itemAdapter.setCartItems(cartItems);
            }
        });
    }
}