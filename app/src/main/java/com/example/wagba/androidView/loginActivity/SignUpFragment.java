package com.example.wagba.androidView.loginActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
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
import com.example.wagba.data.room.entities.User;
import com.example.wagba.data.room.viewmodel.UserViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpFragment extends Fragment implements View.OnClickListener {

    private Button signUp;
    private EditText usernameET, emailET, passwordET;
    private TextView alreadyRegistered;
    private Fragment signInFragment;
    private ProgressBar progressBar;
    private UserViewModel userViewModel;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        signUp = (Button) getActivity().findViewById(R.id.register_button);
        usernameET = (EditText) getActivity().findViewById(R.id.signup_username);
        emailET = (EditText) getActivity().findViewById(R.id.signup_email);
        passwordET = (EditText) getActivity().findViewById(R.id.signup_password);
        alreadyRegistered = (TextView) getActivity().findViewById(R.id.already_registered);
        progressBar = (ProgressBar) getActivity().findViewById(R.id.signup_progressBar);
        userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
        signInFragment = new SignInFragment();

        signUp.setOnClickListener(this);
        alreadyRegistered.setOnClickListener(this);
    }

    public void registration(){
        String username = usernameET.getText().toString().trim();
        String email = emailET.getText().toString().trim();
        String password = passwordET.getText().toString().trim();

        if(username.isEmpty()){
            usernameET.setError("Please enter the full name!");
            usernameET.requestFocus();
            return;
        }

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

        if(password.length()<6){
            passwordET.setError("Password should be longer than 6 characters!");
            passwordET.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                String userID = mAuth.getCurrentUser().getUid();
                Log.i("TAG", "onComplete: "+userID);
                User user = new User(userID, username);
                userViewModel.insertUser(user);

                if(task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Registration successful", Toast.LENGTH_LONG).show();
                    redirect();
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
            case R.id.register_button:
                registration();
                break;
            case R.id.already_registered:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, signInFragment).commit();
        }
    }
}