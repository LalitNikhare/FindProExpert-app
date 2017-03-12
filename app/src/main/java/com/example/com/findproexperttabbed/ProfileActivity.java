package com.example.com.findproexperttabbed;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
    private static final String VIEW_PROFILESKILL_URL = "https://findproexpertcom.000webhostapp.com/skill_fetch.php";
    TextView user_profile_name,text_view_mobno,text_view_email,text_view_skills,user_profile_username;
    ProgressDialog progDiag;
    private static final String USERNAME = "username";
    String username_default;
    boolean flag_profile=false;
   // public static final String  = "https://findproexpertcom.000webhostapp.com/profile_data_fetch.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progDiag = new ProgressDialog(ProfileActivity.this);
        progDiag.setMessage("Reading Response from server...");
        progDiag.show();
        setContentView(R.layout.activity_profile);
        user_profile_name=(TextView)findViewById(R.id.user_profile_name);
        text_view_mobno=(TextView)findViewById(R.id.text_view_mobno);
        text_view_email=(TextView)findViewById(R.id.text_view_email);
        text_view_skills=(TextView)findViewById(R.id.text_view_skills);
        user_profile_username=(TextView)findViewById(R.id.user_profile_username);

        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        username_default= sharedPreferences.getString(Config.USERNAME_SHARED_PREF,"Not Available");

        Intent intent=getIntent();
        if(intent!=null){
            String str=intent.getStringExtra("view");
            if(str.equals("view")){
                flag_profile=true;
                username_default=intent.getStringExtra("username");
            }
        }

        placeDetailRequest();

    }

    public void placeDetailRequest() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, VIEW_PROFILE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(ProfileActivity.this, response, Toast.LENGTH_LONG).show();
                        setDetails(response);
                        progDiag.dismiss();
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
                params.put("username",username_default);
                return params;
            }


        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
//        progDiag = new ProgressDialog(ProfileActivity.this);
//        progDiag.setMessage("Reading Response from server...");
//        progDiag.show();
    }
    public void placeSkillRequest() {
//        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        final String username = sharedPreferences.getString(Config.USERNAME_SHARED_PREF,"Not Available");

        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, VIEW_PROFILESKILL_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response1) {
                        Toast.makeText(ProfileActivity.this, response1, Toast.LENGTH_LONG).show();
                        setSkills(response1);
                        //progDiag.dismiss();
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
                params.put("username",username_default);
                return params;
            }


        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest1);
//        progDiag = new ProgressDialog(ProfileActivity.this);
//        progDiag.setMessage("Reading Response from server...");
//        progDiag.show();
    }

    private void setDetails(String response) {
//        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        final String username = sharedPreferences.getString(Config.USERNAME_SHARED_PREF,"Not Available");

        JSONProfessional jsonProfessional=new JSONProfessional(response);
        jsonProfessional.parseJSONforProfileDetails();

        // Toast.makeText(this,""+JSONProfessional.profile_fname,)

        user_profile_name.setText(""+JSONProfessional.profile_fname+" "+JSONProfessional.profile_lname);
        text_view_mobno.setText("Mobile: "+JSONProfessional.profile_mobile);
        text_view_email.setText("Email: "+JSONProfessional.profile_email);
        text_view_skills.setText("Still Learning new skills.......");
        user_profile_username.setText(JSONProfessional.profile_username);
        placeSkillRequest();

    }

    private void setSkills(String response) {

        JSONProfessional jsonProfessionalSkills=new JSONProfessional(response);
        jsonProfessionalSkills.parseJSONforProfileSkillDetails();

        String prof="";

        for(int i=0;i<JSONProfessional.profile_skills.length;i++){
            Toast.makeText(this,""+JSONProfessional.profile_skills[i],Toast.LENGTH_SHORT).show();
            prof+=""+(i+1)+"."+JSONProfessional.profile_skills[i]+"\n";
        }
        text_view_skills.setText(""+prof);
    }
}
