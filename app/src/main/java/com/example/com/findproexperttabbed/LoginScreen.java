package com.example.com.findproexperttabbed;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.com.findproexperttabbed.HomeScreen.HomeScreen;

import java.util.HashMap;
import java.util.Map;

public class LoginScreen extends AppCompatActivity {

    EditText user,pass;
    Button login;
    TextView cancel;
    public boolean loggedIn=false;
    int workeri,customeri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        user= (EditText) findViewById(R.id.user);
        pass= (EditText) findViewById(R.id.pass);
        login=(Button)findViewById(R.id.login_button2);
        cancel=(TextView) findViewById(R.id.cancel_button1);

        //place checking conditions here --LOGIN
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it1=new Intent(LoginScreen.this,SignUpPage.class);
                startActivity(it1);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //In onresume fetching value from sharedpreference
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        //Fetching the boolean value form sharedpreferences
        loggedIn = sharedPreferences.getBoolean(Config.LOGGEDIN_SHARED_PREF, false);
        String username = sharedPreferences.getString(Config.USERNAME_SHARED_PREF,"Not Available");
        workeri=sharedPreferences.getInt(Config.USER_0CCP_WORKER,-1);
        customeri=sharedPreferences.getInt(Config.USER_0CCP_CUST,-1);

        //If we will get true
        if(loggedIn){
            //We will start the Profile Activity
            Toast.makeText(LoginScreen.this,"Logged In As "+username,Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginScreen.this, HomeScreen.class);
            startActivity(intent);
            finish();
        }
    }
    //please dont change this method
    private void login(){
        //Getting values from edit texts
        final String username = user.getText().toString().trim();
        final String password = pass.getText().toString().trim();


        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(LoginScreen.this,""+response,Toast.LENGTH_SHORT).show();
                        if(!response.equals("Failed")){
                            //Creating a shared preference
//                            Toast.makeText(LoginScreen.this,""+response,Toast.LENGTH_SHORT).show();
                            SharedPreferences sharedPreferences = LoginScreen.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                            JSONProfessional jsonProfessional=new JSONProfessional(response);
                            jsonProfessional.parseJSONforLogin();
                            //Creating editor to store values to shared preferences
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            workeri=JSONProfessional.cust_occ[0];
                            customeri=JSONProfessional.prof_occ[0];
//                            Toast.makeText(LoginScreen.this,""+workeri+ " "+customeri,Toast.LENGTH_SHORT).show();

                            //Adding values to editor
                            editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                            editor.putString(Config.USERNAME_SHARED_PREF, username);
                            editor.putInt(Config.USER_0CCP_CUST,JSONProfessional.cust_occ[0]);
                            editor.putInt(Config.USER_0CCP_WORKER,JSONProfessional.prof_occ[0]);

                            //Saving values to editor
                            editor.commit();

                            //Starting profile activity
                            Intent intent = new Intent(LoginScreen.this, HomeScreen.class);
                            Toast.makeText(LoginScreen.this,"Logged in as "+username,Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                            finish();

                        }else{
                            //If the server response is not success
                            //Displaying an error message on toast
                            Toast.makeText(LoginScreen.this, "Invalid username or password", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                //Adding parameters to request
                params.put(Config.KEY_USERNAME, username);
                params.put(Config.KEY_PASSWORD, password);

                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
