package com.example.com.findproexperttabbed;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class FeedBackPage extends AppCompatActivity {

    RadioButton yes,no;
    RadioGroup grpchoice;
    Button send_feedback;
    int value=0,position;
    String prof_user;
    private static final String FEEDBACK_URL = "https://findproexpertcom.000webhostapp.com/feedback.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back_page);

        Intent in=getIntent();
        prof_user=in.getStringExtra("username");
        position=in.getIntExtra("position",-1);
        yes= (RadioButton) findViewById(R.id.feed_page_yes);
        no= (RadioButton) findViewById(R.id.feed_page_no);
        send_feedback= (Button) findViewById(R.id.send_feedback);

        grpchoice= (RadioGroup) findViewById(R.id.grp_choice);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FeedBackPage.this,"You selected yes",Toast.LENGTH_SHORT).show();
                value=1;


            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FeedBackPage.this,"You selected no",Toast.LENGTH_SHORT).show();
                value=0;

            }
        });

        send_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedBackSend();
                send_feedback.setEnabled(false);

            }
        });
        //feedBackSend();

    }

    private void feedBackSend() {
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String username = sharedPreferences.getString(Config.USERNAME_SHARED_PREF,"Not Available");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, FEEDBACK_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(FeedBackPage.this, response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(FeedBackPage.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String ,String> params=new HashMap<String, String>();
                params.put("username", prof_user);
                params.put("value",String.valueOf(value));

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
