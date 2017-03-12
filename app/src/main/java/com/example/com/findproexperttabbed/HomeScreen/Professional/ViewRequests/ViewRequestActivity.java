package com.example.com.findproexperttabbed.HomeScreen.Professional.ViewRequests;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.com.findproexperttabbed.Config;
import com.example.com.findproexperttabbed.JSONProfessional;
import com.example.com.findproexperttabbed.R;

import java.util.HashMap;
import java.util.Map;

public class ViewRequestActivity extends AppCompatActivity {

    private static final String VIEW_REQUESTS_URL = "https://findproexpertcom.000webhostapp.com/fetch_view_requests.php";
    private static final String DOMAIN = "domain";
    ListView mylist;
    String username,domain;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_request);
        mylist=(ListView)findViewById(R.id.view_request);

        Intent intent=getIntent();
        domain=intent.getStringExtra("domain");

        fetchRequests();

        mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(ViewRequestActivity.this,""+mylist.getSelectedItemPosition(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void fetchRequests(){
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        username = sharedPreferences.getString(Config.USERNAME_SHARED_PREF,"Not Available");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, VIEW_REQUESTS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        viewFetchedRequests(response);
//                        Toast.makeText(ViewRequestActivity.this,""+response,Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ViewRequestActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String ,String> params=new HashMap<String, String>();
                params.put(DOMAIN,domain);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        progressDialog = new ProgressDialog(ViewRequestActivity.this);
        progressDialog.setMessage("Reading Response from server...");
        progressDialog.show();
    }
    public void viewFetchedRequests(String response){
        JSONProfessional jsonProfessional=new JSONProfessional(response);
        jsonProfessional.parseJSONforViewRequest();
        try{
            ViewRequestAdapter cl=new ViewRequestAdapter(ViewRequestActivity.this, JSONProfessional.view_request,JSONProfessional.cust_username);
            mylist.setAdapter(cl);
        }catch(Exception e){

        }

    }
}
