package com.example.wagba;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;


public class CartFragment extends Fragment {

    private FirebaseAuth mAuth;
    private CartAdapter cartAdapter;
    private RecyclerView recyclerView;
    private ArrayList<Cart> carts;
    private UserViewModel userViewModel;
    private FirebaseUser user;
    private TextView noCarts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
        carts = new ArrayList<Cart>();

        noCarts = (TextView) getActivity().findViewById(R.id.noCarts);
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.carts_recycler_view);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        cartAdapter = new CartAdapter(getActivity());
        recyclerView.setAdapter(cartAdapter);

        userViewModel.getUserCarts(user.getUid()).observe(getViewLifecycleOwner(), userCarts ->  {
            carts.clear();
            for (Integer i = 0; i < userCarts.carts.size(); i++) {
                if(userCarts.carts.get(i).getItemCount()<=1){
                    userViewModel.deleteCart(userCarts.carts.get(i));
                }
                carts.add(userCarts.carts.get(i));
            }


            Log.i("ROOM", "onViewCreated: " +carts.size());
            if(!carts.isEmpty()){
                cartAdapter.setCarts(carts);
            }else{
                recyclerView.setVisibility(View.GONE);
                noCarts.setVisibility(View.VISIBLE);
            }
        });



//        int colorFrom = getResources().getColor(R.color.red);
//        int colorTo = getResources().getColor(R.color.blue);
//        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
//        colorAnimation.setDuration(250); // milliseconds
//        colorAnimation.addUpdateListener(new AnimatorUpdateListener() {
//
//            @Override
//            public void onAnimationUpdate(ValueAnimator animator) {
//                textView.setBackgroundColor((int) animator.getAnimatedValue());
//            }
//
//        });
//        colorAnimation.start();



    }
}