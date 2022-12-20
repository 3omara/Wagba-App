package com.example.wagba;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.MealsViewHolder> {

    Context context;
    ArrayList<MealModel> mealModels;

    public MealsAdapter(Context context, ArrayList<MealModel> mealModels) {
        this.context = context;
        this.mealModels = mealModels;
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
        String price = mealModel.getPrice() + " " + "EGP";
        holder.price.setText(price);
        holder.name.setText(mealModel.getName());
        holder.availability.setText(mealModel.getAvailability());

        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(mealModel.getImageURL()).placeholder(R.drawable.ic_baseline_image_24).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mealModels.size();
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
