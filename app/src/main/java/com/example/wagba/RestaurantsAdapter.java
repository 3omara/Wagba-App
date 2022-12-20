package com.example.wagba;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.RestaurantsViewHolder> {

    Context context;
    ArrayList<RestaurantModel> restaurantModels;

    public RestaurantsAdapter(Context context, ArrayList<RestaurantModel> restaurantModels) {
        this.context = context;
        this.restaurantModels = restaurantModels;
    }

    @NonNull
    @Override
    public RestaurantsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.restaurant_item, parent, false);
        return new RestaurantsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantsViewHolder holder, int position) {
        RestaurantModel restaurantModel = restaurantModels.get(position);
        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(restaurantModel.getImage()).placeholder(R.drawable.ic_baseline_image_24).into(holder.rest_image);
        holder.rest_name.setText(restaurantModel.getName());
        holder.rest_address.setText(restaurantModel.getAddress());
        holder.rest_description.setText(restaurantModel.getDescription());
        holder.rest_phone.setText(restaurantModel.getPhone());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToMeals = new Intent(context, MealsActivity.class);
                goToMeals.putExtra("restaurantName", restaurantModel.getName());
                goToMeals.putExtra("restaurantAddress", restaurantModel.getAddress());
                goToMeals.putExtra("restaurantPhone", restaurantModel.getPhone());
                goToMeals.putExtra("restaurantImage", restaurantModel.getImage());
                goToMeals.putExtra("restaurantDescription", restaurantModel.getDescription());
                goToMeals.putExtra("meals", restaurantModel.getMeals());
                context.startActivity(goToMeals);
            }
        });
    }

    @Override
    public int getItemCount() {
        return restaurantModels.size();
    }

    public static class RestaurantsViewHolder extends RecyclerView.ViewHolder{

        TextView rest_name, rest_address, rest_description, rest_phone;
        ImageView rest_image;
        public RestaurantsViewHolder(@NonNull View itemView) {
            super(itemView);
            rest_name = (TextView) itemView.findViewById(R.id.cart_name);
            rest_address = (TextView) itemView.findViewById(R.id.restaurant_address);
            rest_description = (TextView) itemView.findViewById(R.id.cart_price);
            rest_phone = (TextView) itemView.findViewById(R.id.rest_phone);
            rest_image = (ImageView) itemView.findViewById(R.id.restaurant_image);
        }

    }
}
