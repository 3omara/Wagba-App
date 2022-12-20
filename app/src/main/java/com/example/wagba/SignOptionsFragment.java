package com.example.wagba;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SignOptionsFragment extends Fragment {

    private Fragment signInFragment = new SignInFragment();
    private Fragment signUpFragment = new SignUpFragment();
    private Button signInOption;
    private Button signUpOption;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_options, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        signInOption = (Button) getActivity().findViewById(R.id.login_btn);
        signUpOption = (Button) getActivity().findViewById(R.id.signup_btn);

        signInOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, signInFragment).commit();
            }
        });

        signUpOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, signUpFragment).commit();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}