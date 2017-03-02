package com.example.com.findproexperttabbed;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginScreen extends AppCompatActivity {

    EditText username,password;
    Button login,cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        login=(Button)findViewById(R.id.login_button2);
        cancel=(Button)findViewById(R.id.cancel_button1);

        //place checking conditions here --LOGIN
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it1=new Intent(LoginScreen.this,HomeScreen.class);
                startActivity(it1);

            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it1=new Intent(LoginScreen.this,SignUpPage.class);
                startActivity(it1);
            }
        });
    }
}
