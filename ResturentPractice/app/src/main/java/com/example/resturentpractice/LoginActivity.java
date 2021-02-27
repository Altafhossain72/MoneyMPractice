package com.example.resturentpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.resturentpractice.model.User;
import com.example.resturentpractice.retrofit.RetrofitConfig;
import com.example.resturentpractice.service.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private Intent i;
    UserService userService;
    EditText email, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userService = RetrofitConfig.createService(UserService.class);
        email = findViewById(R.id.usermail);
        password = findViewById(R.id.userpas);
    }

    public void navigateSignup(View view){
        i = new Intent(getApplicationContext(), SignupActivity.class);
        startActivity(i);
    }

    public void login(View view){
        String uEmail = email.getText().toString();
        String uPass = password.getText().toString();
        User user  = new User();
        user.setEmail(uEmail);
        user.setPassword(uPass);
        Call<User> userCall = userService.login(user);
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user1 = response.body();
                if(user1 != null){
                    if(user1.getEmail() != null){
                        i = new Intent(getApplicationContext(), Main2Activity.class);
                        startActivity(i);
                    }else{
                        Toast.makeText(getApplicationContext(), "Please check usrname/password", Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
    
}