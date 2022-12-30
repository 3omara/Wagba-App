package com.example.wagba;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private Context context;
    private Cart cart;
    private List<CartItem> cartItems = new ArrayList<>();

    private UserViewModel userViewModel;

    public ItemAdapter(Context context, Cart cart) {
        this.context = context;
        this.cart = cart;
    }

    @NonNull
    @Override
    public ItemAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
        return new ItemAdapter.ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ItemViewHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);

        holder.item_name.setText(cartItem.getItemName());
        holder.item_restaurant.setText(cartItem.getItemRestaurant());

        DecimalFormat format = new DecimalFormat("#.00");
        String price = format.format(cartItem.getItemPrice());
        holder.item_cost.setText(price);

        String itemNum = Integer.toString(cartItem.getItemNum());
        holder.item_count.setText(itemNum);


        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(cartItem.getImageURL()).placeholder(R.drawable.ic_baseline_image_24).into(holder.itemImage);

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Float newCost = cart.getCartCost()+cartItem.getItemPrice();
                Integer newItemCount = cart.getItemCount()+1;
                Cart updatedCart = new Cart(cart.getCartID(), cart.getUserID(), cart.getCartName(), newItemCount, newCost );
                CartItem updatedItem = new CartItem(cartItem.getUserID(), cartItem.getCartID(), cartItem.getItemName(), cartItem.getItemRestaurant(), cartItem.getItemNum()+1, cartItem.getItemPrice(), cartItem.getImageURL());
                cart.setCartCost(newCost);
                cart.setItemCount(newItemCount);
                userViewModel.updateItem(updatedItem);
                userViewModel.updateCart(updatedCart);
            }
        });

        holder.subtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cartItem.getItemNum()<=1){
                    userViewModel.deleteCartItem(cartItem);
                }else {
                    Integer newItemNum = cartItem.getItemNum() - 1;
                    CartItem updatedItem = new CartItem(cartItem.getUserID(), cartItem.getCartID(), cartItem.getItemName(), cartItem.getItemRestaurant(), newItemNum, cartItem.getItemPrice(), cartItem.getImageURL());
                    cartItem.setItemNum(newItemNum);
                    userViewModel.updateItem(updatedItem);
                }

                Float newCost = cart.getCartCost() - cartItem.getItemPrice();
                Integer newItemCount = cart.getItemCount() - 1;
                Cart updatedCart = new Cart(cart.getCartID(), cart.getUserID(), cart.getCartName(), newItemCount, newCost);
                cart.setCartCost(newCost);
                cart.setItemCount(newItemCount);
                userViewModel.updateCart(updatedCart);
                if(cart.getItemCount()<=0){
                    Intent intent = new Intent(context, HomeActivity.class);
                    context.startActivity(intent);
                    Toast.makeText(context, "Cart deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if(userViewModel==null){
            userViewModel=new ViewModelProvider((ViewModelStoreOwner) recyclerView.getContext()).get(UserViewModel.class);
        }
    }

    public void setCart(Cart cart) {
        this.cart = cart;
        notifyDataSetChanged();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView item_restaurant, item_count, item_cost, item_name;
        FloatingActionButton add, subtract;
        ImageView itemImage;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            item_name = (TextView) itemView.findViewById(R.id.item_name);
            item_restaurant = (TextView) itemView.findViewById(R.id.item_restaurant);
            item_count = (TextView) itemView.findViewById(R.id.meal_count);
            item_cost = (TextView) itemView.findViewById(R.id.item_cost);


            itemImage = (ImageView) itemView.findViewById(R.id.item_image);

            add = (FloatingActionButton) itemView.findViewById(R.id.add_floatingActionButton);
            subtract = (FloatingActionButton) itemView.findViewById(R.id.subtract_floatingActionButton);

        }
    }
}
