package com.example.wagba;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.MealsViewHolder> {

    private Context context;
    private ArrayList<MealModel> mealModels;
    private String restaurantName;
    private Boolean cartExists = Boolean.FALSE;
    private String userID;

    private LifecycleOwner lifecycleOwner;
    private ArrayList<Cart> carts;
    private UserViewModel userViewModel;
    private CartWithCartItems cartWithCartItems;

    public MealsAdapter(Context context, ArrayList<MealModel> mealModels, String restaurantName, String userID, LifecycleOwner lifecycleOwner) {
        this.context = context;
        this.mealModels = mealModels;
        this.restaurantName = restaurantName;
        this.userID = userID;
        this.lifecycleOwner = lifecycleOwner;
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

//        AtomicReference<Boolean> itemExists = new AtomicReference<>(Boolean.FALSE);
//        AtomicReference<CartItem> cartItem = new AtomicReference<>();
//        if(cartExists){
//            userViewModel.getCartItems(oldCart.getCartID()).observe(lifecycleOwner, cartItems -> {
//                for (Integer i = 0; i < cartItems.cartItems.size(); i++) {
//                    if (cartItems.cartItems.get(i).getItemName().equals(mealModel.getName())) {
//                        cartItem.set(cartItems.cartItems.get(i));
//                        itemExists.set(Boolean.TRUE);
//                    }
//                    Log.i("ROOM", "Comparison: " + cartItems.cartItems.get(i).getItemName() + " vs " + mealModel.getName() + ": " + itemExists.get());
//                }
//            });
//        }

        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cartName = restaurantName + "\'s Cart";
                AtomicReference<Boolean> itemExists = new AtomicReference<>(Boolean.FALSE);
                AtomicReference<CartItem> cartItem = new AtomicReference<>();

                if(cartExists){
                    Cart cart = new Cart(cartWithCartItems.cart.getCartID(), userID, cartName, cartWithCartItems.cart.getItemCount()+1, cartWithCartItems.cart.getCartCost()+Float.parseFloat(mealModel.getPrice()));
                    userViewModel.updateCart(cart);

                    for(Integer i = 0; i<cartWithCartItems.cartItems.size(); i++){
                        if(cartWithCartItems.cartItems.get(i).getItemName().equals(mealModel.getName())){
                            cartItem.set(cartWithCartItems.cartItems.get(i));
                            itemExists.set(Boolean.TRUE);
                        }
                    }

                    if(itemExists.get()){
                        CartItem item = cartItem.get();
                        CartItem updatedItem = new CartItem(item.getUserID(), item.getCartID(), item.getItemName(), item.getItemRestaurant(), item.getItemNum()+1, item.getItemPrice(), item.getImageURL());
                        userViewModel.updateItem(updatedItem);
                    }else {
                        CartItem newItem = new CartItem(userID, cartWithCartItems.cart.getCartID(), mealModel.getName(), restaurantName, 1, Float.parseFloat(mealModel.getPrice()), mealModel.getImageURL());
                        userViewModel.insertCartItem(newItem);
                    }

                }else{
                    Cart cart = new Cart(0, userID, cartName, 1, Float.parseFloat(mealModel.getPrice()));
                    Long cartID = null;
                    try {
                        cartID = userViewModel.insertCart(cart);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.i("ROOM", "onClick: " + cartID);
                    CartItem newCartItem = new CartItem(userID, cartID, mealModel.getName(), restaurantName, 1, Float.parseFloat(mealModel.getPrice()), mealModel.getImageURL());
                    userViewModel.insertCartItem(newCartItem);
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

    public void setCartExists(Boolean cartExists) {
        this.cartExists = cartExists;
        notifyDataSetChanged();
    }

    public void setCartWithCartItems(CartWithCartItems cartWithCartItems) {
        this.cartWithCartItems = cartWithCartItems;
        notifyDataSetChanged();
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
