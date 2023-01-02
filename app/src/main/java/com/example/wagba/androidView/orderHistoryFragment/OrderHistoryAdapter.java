package com.example.wagba.androidView.orderHistoryFragment;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wagba.R;
import com.example.wagba.models.Order;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryViewHolder> {

    ArrayList<Order> orders;
    Context context;

    public OrderHistoryAdapter(ArrayList<Order> orders, Context context) {
        this.orders = orders;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderHistoryAdapter.OrderHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.order_item, parent, false);
        return new OrderHistoryAdapter.OrderHistoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryAdapter.OrderHistoryViewHolder holder, int position) {
        Order order = orders.get(position);

        holder.orderDate.setText(order.getDate().toString());

        DecimalFormat format = new DecimalFormat("#.00");
        String price = format.format(order.getPrice());
        price = price + " L.E.";
        holder.orderCost.setText(price);
        holder.orderRestaurant.setText(order.getRestaurantName());
        holder.orderPickup.setText(order.getTimeSlot());
        holder.orderGate.setText(Integer.toString(order.getGate()));
        holder.orderPayStatus.setText(order.getPayStatus());
        holder.orderDeliveryStatus.setText(order.getDeliveryStatus());

        if(order.getPayStatus().equals("Payment due")){
            holder.orderPayStatus.setTextColor(Color.RED);
        }
        if(order.getDeliveryStatus().equals("Cancelled")){
            holder.orderPayStatus.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class OrderHistoryViewHolder extends RecyclerView.ViewHolder{

        TextView orderDate, orderCost, orderGate, orderPickup, orderDeliveryStatus, orderPayStatus, orderRestaurant;

        public OrderHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            orderDate = (TextView) itemView.findViewById(R.id.order_date);
            orderCost = (TextView) itemView.findViewById(R.id.order_price);
            orderGate = (TextView) itemView.findViewById(R.id.gate_number);
            orderPickup = (TextView) itemView.findViewById(R.id.pickup_time);
            orderPayStatus = (TextView) itemView.findViewById(R.id.pay_status);
            orderDeliveryStatus = (TextView) itemView.findViewById(R.id.delivery_status);
            orderRestaurant = (TextView) itemView.findViewById(R.id.order_restaurant);
        }
    }
}
