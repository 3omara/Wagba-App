package com.example.wagba.androidView.loginActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wagba.R;
import com.example.wagba.androidView.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInFragment extends Fragment implements View.OnClickListener {

    private Button signIn;
    private EditText emailET, passwordET;
    private TextView register;
    private ProgressBar progressBar;
    private Fragment signUpFragment;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        signIn = (Button) getActivity().findViewById(R.id.signin_button);
        emailET = (EditText) getActivity().findViewById(R.id.signin_email);
        passwordET = (EditText) getActivity().findViewById(R.id.signin_password);
        register = (TextView) getActivity().findViewById(R.id.register);
        progressBar = (ProgressBar) getActivity().findViewById(R.id.signin_progressBar);
        signUpFragment = new SignUpFragment();

        mAuth = FirebaseAuth.getInstance();

        signIn.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    public void signingIn(){
        String email = emailET.getText().toString().trim();
        String password = passwordET.getText().toString().trim();

        if(email.isEmpty() | !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailET.setError("Please enter a valid email!");
            emailET.requestFocus();
            return;
        }

        if(password.isEmpty()){
            passwordET.setError("Please enter a password!");
            passwordET.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    redirect();
                }else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Failed to log in. Please check your credentials!", Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    public void redirect(){
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        getActivity().startActivity(intent);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signin_button:
                signingIn();
                break;
            case R.id.register:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, signUpFragment).commit();

        }
    }
}