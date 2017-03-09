package com.example.com.findproexperttabbed;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    private static final String VIEW_PROFILE_URL = "https://findproexpertcom.000webhostapp.com/profile_data_fetch.php";
    TextView user_profile_name,text_view_mobno,text_view_email,text_view_skills,user_profile_username;
    private static final String USERNAME = "username";
   // public static final String  = "https://findproexpertcom.000webhostapp.com/profile_data_fetch.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        user_profile_name=(TextView)findViewById(R.id.user_profile_name);
        text_view_mobno=(TextView)findViewById(R.id.text_view_mobno);
        text_view_email=(TextView)findViewById(R.id.text_view_email);
        text_view_skills=(TextView)findViewById(R.id.text_view_skills);
        user_profile_username=(TextView)findViewById(R.id.user_profile_username);

        placeDetailRequest();
    }

    public void placeDetailRequest() {
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String username = sharedPreferences.getString(Config.USERNAME_SHARED_PREF,"Not Available");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, VIEW_PROFILE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(ProfileActivity.this, response, Toast.LENGTH_LONG).show();
                        setDetails(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ProfileActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String ,String> params=new HashMap<String, String>();
                params.put("username",username);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void setDetails(String response) {
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String username = sharedPreferences.getString(Config.USERNAME_SHARED_PREF,"Not Available");

        JSONProfessional jsonProfessional=new JSONProfessional(response);
        jsonProfessional.parseJSONforProfileDetails();
       // Toast.makeText(this,""+JSONProfessional.profile_fname,)

        user_profile_name.setText(""+JSONProfessional.profile_fname+" "+JSONProfessional.profile_lname);
       text_view_mobno.setText("Mobile: "+JSONProfessional.profile_mobile);
       text_view_email.setText("Email: "+JSONProfessional.profile_email);
       text_view_skills.setText("Still Learning new skills.......");
       user_profile_username.setText(JSONProfessional.profile_username);
    }
}
