package com.uzair.kptehsilmunicipaladministrationservices.LoginAndSignUp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.uzair.kptehsilmunicipaladministrationservices.Main.MainActivity;
import com.uzair.kptehsilmunicipaladministrationservices.R;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ImageButton button = findViewById(R.id.signInBtn);
        Button createAccountBtn = findViewById(R.id.txtCreateNewAccount);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Login.this , MainActivity.class));
            }
        });

        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this , SignUp.class));
            }
        });
    }
}
