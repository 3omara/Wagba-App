package com.example.wagba.androidView.mealsActivity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wagba.R;
import com.example.wagba.data.room.entities.Cart;
import com.example.wagba.data.room.entities.CartItem;
import com.example.wagba.data.room.entities.CartWithCartItems;
import com.example.wagba.data.models.MealModel;
import com.example.wagba.data.room.viewmodel.UserViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.MealsViewHolder> {

    private Context context;
    private ArrayList<MealModel> mealModels;
    private String restaurantName;
    private Boolean cartExists = Boolean.FALSE;
    private String userID;

    private UserViewModel userViewModel;


    public MealsAdapter(Context context, ArrayList<MealModel> mealModels, String restaurantName, String userID) {
        this.context = context;
        this.mealModels = mealModels;
        this.restaurantName = restaurantName;
        this.userID = userID;
    }

    @NonNull
    @Override
    public MealsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.meal_item, parent, false);
        return new MealsAdapter.MealsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MealsViewHolder holder, int position) {
        MealModel mealModel = mealModels.get(position);
        String price = mealModel.getPrice() + " " + "L.E.";
        holder.price.setText(price);
        holder.name.setText(mealModel.getName());

        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cartName = restaurantName + "\'s Cart";
                AtomicReference<Boolean> itemExists = new AtomicReference<>(Boolean.FALSE);
                AtomicReference<CartItem> cartItem = new AtomicReference<>();
                CartWithCartItems cartWithCartItems = null;
                Cart oldCart = null;

                try {
                    oldCart = userViewModel.getCartByName(userID, cartName);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    Log.i("Improve", "onClick: 72");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.i("Improve", "onClick: 75");
                }
                Log.i("Improve", "onClick: 77");

                if(oldCart != null){
                    Log.i("Improve", "onClick: 80 ... " + oldCart.getUserID() + " vs " + userID);
                    try {
                        cartWithCartItems = userViewModel.getNonLiveCartItems(oldCart.getCartID());
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Cart cart = new Cart(oldCart.getCartID(), userID, cartName, oldCart.getItemCount()+1, oldCart.getCartCost()+Float.parseFloat(mealModel.getPrice()));
                    userViewModel.updateCart(cart);

                    if(cartWithCartItems!=null){
                        for (Integer i = 0; i < cartWithCartItems.cartItems.size(); i++) {
                            if (cartWithCartItems.cartItems.get(i).getItemName().equals(mealModel.getName())) {
                                cartItem.set(cartWithCartItems.cartItems.get(i));
                                itemExists.set(Boolean.TRUE);
                            }
                        }

                        if (itemExists.get()) {
                            CartItem item = cartItem.get();
                            CartItem updatedItem = new CartItem(item.getUserID(), item.getCartID(), item.getItemName(), item.getItemRestaurant(), item.getItemNum() + 1, item.getItemPrice(), item.getImageURL());
                            userViewModel.updateItem(updatedItem);
                        } else {
                            CartItem newItem = new CartItem(userID, cartWithCartItems.cart.getCartID(), mealModel.getName(), restaurantName, 1, Float.parseFloat(mealModel.getPrice()), mealModel.getImageURL());
                            userViewModel.insertCartItem(newItem);
                        }
                    }

                }else{
                    Cart cart = new Cart(0, userID, cartName, 1, Float.parseFloat(mealModel.getPrice()));
                    Long cartID = null;
                    Log.i("Improve", "onClick: 106");
                    try {
                        cartID = userViewModel.insertCart(cart);
                        Log.i("Improve", "onClick: 109");
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.i("ROOM", "onClick: " + cartID);
                    if (cartID!=null){
                        CartItem newCartItem = new CartItem(userID, cartID, mealModel.getName(), restaurantName, 1, Float.parseFloat(mealModel.getPrice()), mealModel.getImageURL());
                        userViewModel.insertCartItem(newCartItem);
                    }
                }
            }
        });

        holder.availability.setText(mealModel.getAvailability());
        if(mealModel.getAvailability().equals("Not Available")){
            holder.availability.setTextColor(context.getResources().getColor(R.color.red));
            holder.addToCart.setEnabled(false);
        }

        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(mealModel.getImageURL()).placeholder(R.drawable.ic_baseline_image_24).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mealModels.size();
    }


    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if(userViewModel==null){
            userViewModel=new ViewModelProvider((ViewModelStoreOwner) recyclerView.getContext()).get(UserViewModel.class);
        }
    }


    public static class MealsViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView name, price, availability;
        FloatingActionButton addToCart;

        public MealsViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.meal_image);
            name = (TextView) itemView.findViewById(R.id.meal_name);
            availability = (TextView) itemView.findViewById(R.id.meal_availability);
            price = (TextView) itemView.findViewById(R.id.meal_price);
            addToCart = (FloatingActionButton) itemView.findViewById(R.id.add_to_cart);
        }
    }
}
