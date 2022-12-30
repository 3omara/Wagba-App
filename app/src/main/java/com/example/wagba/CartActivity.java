package com.example.wagba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private UserViewModel userViewModel;
    private Intent intent;
    private Cart cart;
    private TextView cartTotalCost;
    private MaterialButtonToggleGroup pickupChoices, gateChoices;
    private Button noon, afternoon, gate4, gate3;
    private ProgressBar progressBar;
    private Button confirmButton;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private DatabaseReference ordersRef;
    private Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        intent = getIntent();
        cart = (Cart) getIntent().getSerializableExtra("cart");
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        cartTotalCost = (TextView) findViewById(R.id.cart_total_cost);
        pickupChoices = (MaterialButtonToggleGroup) findViewById(R.id.pickup_choices);
        gateChoices = (MaterialButtonToggleGroup) findViewById(R.id.gate_choices);
        noon = (Button) findViewById(R.id.noon);
        afternoon = (Button) findViewById(R.id.afternoon);
        gate3 = (Button) findViewById(R.id.gate3);
        gate4 = (Button) findViewById(R.id.gate4);
        confirmButton = (Button) findViewById(R.id.confirmation_button);
        confirmButton.setBackgroundColor(Color.GRAY);
        progressBar = (ProgressBar) findViewById(R.id.order_progressBar);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders");

        intent = new Intent(this, HomeActivity.class);

        recyclerView = (RecyclerView) findViewById(R.id.items_RecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemAdapter = new ItemAdapter(this, cart);
        recyclerView.setAdapter(itemAdapter);

        DecimalFormat format = new DecimalFormat("#.00");
        String totalPrice = format.format(cart.getCartCost());
        cartTotalCost.setText(totalPrice);

        confirmButton.setEnabled(false);

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timer timer = new Timer();
        int period = 1000*60*60*24;
        date = null;
        String noonDate = "2012-07-06 10:00:00";
        String afternoonDate = "2012-07-06 13:00:00";
        String newDay = "2012-07-06 12:00:00";
        try {
            date = dateFormatter.parse(noonDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        timer.schedule(new ToggleButtonAvailability(noon, false), date, period );
        try {
            date = dateFormatter.parse(newDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        timer.schedule(new ToggleButtonAvailability(noon, true), date, period );
        timer.schedule(new ToggleButtonAvailability(afternoon, true), date, period );
        try {
            date = dateFormatter.parse(afternoonDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        timer.schedule(new ToggleButtonAvailability(afternoon, false), date, period );

        noon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                toggleCheck();
            }
        });

        afternoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleCheck();
            }
        });

        gate4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleCheck();
            }
        });

        gate3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleCheck();
            }
        });


        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CartWithCartItems cartItems = null;
                try {
                    cartItems = userViewModel.getNonLiveCartItems(cart.getCartID());
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Integer gateChoice = gateChoices.getCheckedButtonId();
                Integer pickupChoice = pickupChoices.getCheckedButtonId();
                Log.i("ORDERS", "onClick: " + gateChoice + "   " + pickupChoice);
                Integer gate = 3;
                String pickup = "";
                Float orderPrice;
                HashMap<String, Integer> orderItems = new HashMap<>();

                switch(gateChoice){
                    case R.id.gate3:
                        gate = 3;
                        break;
                    case R.id.gate4:
                        gate = 4;
                        break;
                    default:
                        break;
                }
                switch (pickupChoice){
                    case R.id.noon:
                        pickup = "12:00 PM";
                        break;
                    case R.id.afternoon:
                        pickup = "03:00 PM";
                    default:
                        break;
                }
                orderPrice = cart.getCartCost() + 10;

                for(Integer i = 0; i<cartItems.cartItems.size(); i++){
                    orderItems.put(cartItems.cartItems.get(i).getItemName(), cartItems.cartItems.get(i).getItemNum());
                }
                String restaurantName = cart.getCartName().split("\'")[0];
                Order order = new Order(user.getUid(),restaurantName,  orderPrice, gate, pickup,orderItems);
                progressBar.setVisibility(View.VISIBLE);
                ordersRef.push().setValue(order).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressBar.setVisibility(View.GONE);
                        if(task.isSuccessful()){

                            Cart updatedCart = new Cart(cart.getCartID(), cart.getUserID(), cart.getCartName(), 0, cart.getCartCost());
                            userViewModel.updateCart(updatedCart);
                            Toast.makeText(CartActivity.this, "Order Successful", Toast.LENGTH_LONG).show();
                            startActivity(intent);
                        }
                    }
                });

            }
        });


        userViewModel.getCartItems(cart.getCartID()).observe(this, cartItemsObserver->{
            if(cartItemsObserver.cartItems != null){
                itemAdapter.setCartItems(cartItemsObserver.cartItems);
            }
        });
    }

    public void toggleCheck(){
        if(pickupChoices.getCheckedButtonId()==-1 || gateChoices.getCheckedButtonId() ==-1){
            confirmButton.setEnabled(false);
            confirmButton.setBackgroundColor(Color.GRAY);
        }else {
            confirmButton.setEnabled(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                confirmButton.setBackgroundColor(getColor(R.color.teal_200));
            }else{
                confirmButton.setBackgroundColor(Color.CYAN);
            }
        }
    }



}