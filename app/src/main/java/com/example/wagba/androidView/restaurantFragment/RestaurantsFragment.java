package com.example.wagba.androidView.restaurantFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wagba.R;
import com.example.wagba.data.models.RestaurantModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RestaurantsFragment extends Fragment {

    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private RestaurantsAdapter restaurantsAdapter;
    private ArrayList<RestaurantModel> restaurantModels;

    public RestaurantsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_restaurants, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.restaurants_recycler);
        databaseReference = FirebaseDatabase.getInstance().getReference("Restaurants");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        restaurantModels = new ArrayList<>();
        restaurantsAdapter = new RestaurantsAdapter(getActivity(), restaurantModels);
        recyclerView.setAdapter(restaurantsAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
//                    GenericTypeIndicator<HashMap<String, MealModel>> t = new GenericTypeIndicator<HashMap<String, MealModel>>() {};
//                    HashMap<String, MealModel> meals = dataSnapshot.child("meals").getValue(t);

                    RestaurantModel restaurantModel = dataSnapshot.getValue(RestaurantModel.class);

                    restaurantModels.add(restaurantModel);
                }
                restaurantsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}