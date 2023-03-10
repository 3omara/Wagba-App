package com.example.wagba.androidView.cartFragment;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wagba.R;
import com.example.wagba.androidView.cartActivity.CartActivity;
import com.example.wagba.data.room.entities.Cart;
import com.example.wagba.data.room.entities.CartWithCartItems;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder>{

    Context context;
    ArrayList<CartWithCartItems> carts = new ArrayList<>();

    public CartAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CartAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cart_fragment_item, parent, false);
        return new CartAdapter.CartViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CartViewHolder holder, int position) {
        Cart cart = carts.get(position).cart;
        Log.i("ROOM", "Inside: " + cart.getCartName());

        DecimalFormat format = new DecimalFormat("#.00");
        String price = format.format(cart.getCartCost());
        price = price + " L.E.";
        holder.cartPrice.setText(price);

        holder.cartName.setText(cart.getCartName());

        String itemN = Integer.toString(cart.getItemCount()) + " items";
        holder.itemCount.setText(itemN);

        holder.cartDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToCartItems = new Intent(context, CartActivity.class);
                goToCartItems.putExtra("cart", carts.get(holder.getAdapterPosition()).cart);
                context.startActivity(goToCartItems);
            }
        });
    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    public void setCartsWithCartItems(ArrayList<CartWithCartItems> carts) {
        this.carts = carts;
        notifyDataSetChanged();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder{
        TextView cartName, itemCount, cartPrice;
        LinearLayout cartDetails;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            cartName = (TextView) itemView.findViewById(R.id.cart_name);
            itemCount = (TextView) itemView.findViewById(R.id.item_count);
            cartPrice = (TextView) itemView.findViewById(R.id.cart_price);

            cartDetails = (LinearLayout) itemView.findViewById(R.id.cart_details);
        }
    }
}
